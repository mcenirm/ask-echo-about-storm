#!/bin/bash

case "$#" in
  1)  stormid="$1" ;;
  *)  echo "Usage: $0 yyyynnb"
      exit 1
      ;;
esac

stormdir="out/storm-$stormid"

function warn() {
  printf -v _warn_msg ' %s' "$@"
  printf 1>&2 'warn:%s\n' "$_warn_msg"
}

function filch() {
  url="$1"
  file="$2"
  log="$file"'.log'

  RETVAL=0
  if ! [ -e "$file" ] ; then
    warn filch "$file" from "$url"
    wget -a "$log" -O "$file" "$url"
    RETVAL=$?
  fi
  return $RETVAL
}

if ! [ -d "$stormdir" ] ; then
  echo "bad stormdir \"$stormdir\""
  exit 1
fi

for trackdir in "$stormdir"/track-* ; do
  . <( sed -n -e 's,^<track \(.*\)/>$,\1,p' "$trackdir"/track-"$stormid"-*.xml )
  #declare -p pressure windspeed longitude latitude category date
  printf -v box '%.1f,%.1f,%.1f,%.1f' $( echo $longitude - 2.5 | bc ) $( echo $latitude - 2.5 | bc ) $( echo $longitude + 2.5 | bc ) $( echo $latitude + 2.5 | bc )
  start="$date"Z
  end="$date"Z
  cursor=1
  while [ "$cursor" -gt 0 ] ; do
    url='https://api.echo.nasa.gov:443/echo-esip/search/dataset.atom?boundingBox='"$box"'&startTime='"$start"'&endTime='"$end"'&cursor='"$cursor"'&numberOfResults=1'
    printf -v datasetfile '%s/dataset-%04d.xml' "$trackdir" "$cursor"
    if filch "$url" "$datasetfile" && [ -s "$datasetfile" ] ; then
      count=$( xsltproc count-atom-entries.xsl "$datasetfile" )
      result=$?
      if [ "$result" -eq 0 ] ; then
        if [ "$count" -ne 1 ] ; then
          echo "$count $datasetfile"
          break
        fi
        printf -v granuleurl '%s&boundingBox=%s&startTime=%s&endTime=%s&cursor=%s&numberOfResults=%s' $( xsltproc extract-granule-search-url.xsl "$datasetfile" ) "$box" "$start" "$end" 1 1000
        filch "$granuleurl" "${datasetfile%.xml}"-granules.xml
      fi
    fi
    let cursor++
  done
done


