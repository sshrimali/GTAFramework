#! /bin/bash
WORK_DIR=$(pwd)

video_dir=$WORK_DIR/reports/html/videos/
convert()
{
    video=$1
 	rm -f ${video}.mp4
 	$WORK_DIR/scripts/ffmpeg -loglevel panic -i ${video}.avi -vcodec libx264  -crf 25 -acodec libfaac -threads 0 -t 60 "${video}.mp4"
 	rm -f ${video}.avi

}
merge_videos()
{
    source $WORK_DIR/config/config.cfg
    for test in $prime_test_scenario_ids
    do
        echo file "test_"$test"_movie.mp4";
        done > $video_dir/prime_scenarios.log
        $WORK_DIR/scripts/ffmpeg -loglevel panic -f concat -i $video_dir/prime_scenarios.log -vcodec copy -y -c copy $video_dir/user_stories.mp4
}



#####Start Execution####
export videos=$(ls ${video_dir}/*.avi |sed s/.avi//g)

for video in $videos
do
	convert $video 
done

merge_videos
