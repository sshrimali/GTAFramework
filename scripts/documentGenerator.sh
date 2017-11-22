#! /bin/bash

mapFile=work_area/map.txt
htmlDocFile=docs/GTAFrameworkAutomationGuide.html

# Generate map
grep -r public  src/utils/selenium/* |grep '(' |sed s/public//g |sed s/void//g |cut -d ')' -f 1 |grep -v setImageLib |grep -v 'navigateTo(' |grep -v "Helper.*Helper" |sed s/boolean//g |tr -d '\t' | tr -s ' ' > $mapFile

paramsUsedInFunction()
{
 #echo i am variables used :
 #echo -ne "    \033[0;31m \n"
 #method
 #echo "$@" | grep '(' | grep -v "()"
 #get selenium used data
 echo "$@" | grep '(' | grep -v "()" |grep 'UI\.' |grep -o "(.*)" | tr -d "()"
 #get sikuli based data
 # get internal methods data
 # get external methods data
 #echo -ne "\n\n\n\033[0m"
}

getTestDataAttributesInClass()
{
 classNameLocal=$1
 #method
 #echo "$@" | grep '(' | grep -v "()"
 #get selenium used data
 #export testDataAttribs=$(grep 'testDataReader.getData' $classNameLocal |grep -o "(.*)" |cut -d ',' -f 1 | tr -d '()" ')
 export testDataAttribs=$(grep 'testDataReader.getData' $classNameLocal| sed 's/=.*(/:/g' | cut -d ',' -f 1 | tr -d '()" ;')
 #echo DataObject is :
 grep 'testDataReader.getData' $classNameLocal| sed 's/=.*(/:/g' | cut -d ',' -f 1 | tr -d '()" ;'
 #echo -ne "\n\n\n\033[0m"
}

getSampleTestData()
{
    dataAttribsInFunction=$1;shift
    dataSampleInFunction=""
    let randomNumber="$((1000 + RANDOM % 9999))"
    for dataAttribute in $dataAttribsInFunction
    do
        dataSample=$(grep -R -m 1 -o "$dataAttribute=.*" ./testdata/ |cut -d ':' -f 2)
        dataSampleInFunction="$dataSampleInFunction
${randomNumber}_${dataSample}"
        #echo "This is data"
        #echo "$dataSampleInFunction"
    done
    export dataSampleInFunction="$(echo "$dataSampleInFunction" |grep [a-z].* )"
    echo $dataSampleInFunction
}

getListOfUsedAttributesInFunction()
{
    #local_testDataAttributesInClass="$(echo "$1" |cut -d ':' -f 1)";shift
    local_testDataAttributesInClass="$1";shift
    local_func_data="$1"
    #echo local attrib is : $local_testDataAttributesInClass >&1
    #echo 1: $local_testDataAttributesInClass
    #echo 2: $local_func_data
    dataUsedInFunction=""
    for data in $local_testDataAttributesInClass
    do
        data1=$(echo $data|cut -d ":" -f 1)
        usedAttrib1=$(echo "$local_func_data" |grep "$data1[,) ]" |grep -o $data1 |sort | uniq)
        if [ ! -z $usedAttrib1 ]
        then
            echo "$local_testDataAttributesInClass" |grep $usedAttrib1 |sed 's/:_/:/g' |cut -d ':' -f 2
            fi
        #if echo "$local_func_data" |grep --q "$data[,) ]"
        #then
        #    echo Found data as
        #    echo "$local_func_data" |grep "$data[,) ]"
        #    dataUsedInFunction="$dataUsedInFunction $data"
        #fi

    done
    #export dataUsedInFunction=$dataUsedInFunction
}
getCanonicalName()
{
    name=$1;shift
    name=$(echo $name | sed 's/\([^[:blank:]]\)\([[:upper:]]\)/\1 \2/g')
    if echo $name |grep -q "^is "
    then
        name="$(echo $name |sed "s/^is /to verify if /g")"
        fi
    if echo $name |grep -qb "UI"
    then
        name="$(echo $name |sed "s/ UI/ frontend/g")"
        fi
    echo $name
}
#Process map
updateDoc()
{
    documentFile=FrameworkGuide.html
    oldClassName=""
    echo "" > $documentFile
    map=""
    classCounter=0;
    methodCounter=0;
    while read LINE
    do
        #echo $LINE
        className=$(echo $LINE|cut -d ':' -f 1)
        completeMethodName=$(echo $LINE|cut -d ':' -f 2)
        methodName=$(echo $completeMethodName | cut -d '(' -f 1)
        attribName=$(echo $completeMethodName | cut -d '(' -f 2)
        echo Class is : $className
        echo Method is : $methodName
        #echo attribs are : $attribName

        func_data=$(sed -n "/$completeMethodName/,/throws /{/$completeMethodName/d;/throws /d;p;}" $className)
        #echo "$func_data" |grep -v screen |grep -v 'UI.' |grep -o "\..*(" |cut -d '(' -f 1 |cut -d '.' -f 2
        #echo -ne "\n"
        #echo -ne "    \033[0;31m Referances Used are : \n"
        #echo -ne "\n"
        #echo -ne func data is :\n "$func_data"
        #echo -ne "\n"
        helperUsed=$(echo "$func_data" |grep Helper |grep new |cut -d '=' -f 1 |tr -s " " |sed "s/^ //g" |sed "s/ $//g" |cut -d " " -f 1 |tr -d "\t")
        helperUsedPath="src/utils/selenium/${helperUsed}.java"
        objectUsed=$(echo "$func_data" |grep Helper |grep new |cut -d '=' -f 1 |tr -s " " |sed "s/^ //g" |sed "s/ $//g" |cut -d " " -f 2)
        #paramsUsedInFunction=$(paramsUsedInFunction "$func_data")

        testDataAttributesInClass=$(getTestDataAttributesInClass $className)
        #echo -ne "here starting data in class: \n"
        #echo -ne "$testDataAttributesInClass"
        #echo -ne "\n here stop"
         #echo -ne "\nhere starting data in function : \n"
        testDataAttributesUsedInMethod=$(getListOfUsedAttributesInFunction "$testDataAttributesInClass" "$func_data")

        #dataAttribsInFunction="$attribName";
        dataAttribsInFunction="";
        dataAttribsInFunction=""$dataAttribsInFunction"
        "$testDataAttributesUsedInMethod""
        #echo "$dataAttribsInFunction"
         #echo -ne "here stopping data in function: \n"
         # Find object based method calls
        if [[ ! -z "$objectUsed" ]]
        then

            #methodUsed=$(echo "$func_data" |grep $objectUsed | grep -o "\..*(" |cut -d '(' -f 1 |cut -d '.' -f 2)
            methodsUsedList=$(echo "$func_data" |grep $objectUsed | grep -o "\..*(" |cut -d '(' -f 1  |cut -d '.' -f 2)
            for methodUsed in $methodsUsedList
             do
                #echo "Map : "$Class : $Method : $helperUsed : $objectUsed : $methodUsed
                used_func_data=$(sed -n "/$methodUsed/,/public /{/$methodUsed/d;/public /d;p;}" $helperUsedPath)
                map="$map\n$className : $methodName : $helperUsed : $objectUsed : "$methodUsed
                #getTestDataAttributesInClass $helperUsedPath
                testDataAttributesInClass=$(getTestDataAttributesInClass $helperUsedPath)
                #getListOfUsedAttributesInFunction "$used_func_data"
                #echo Used class is : $helperUsedPath
                #echo Used Method is : $methodUsed
                dataAttribsInFunction=""$dataAttribsInFunction"
                $(getListOfUsedAttributesInFunction "$testDataAttributesInClass" "$used_func_data")"
             done
        fi
        # Find local methods based functions
        functionsInClass=$( grep '(' $className |grep ')' | grep "public\|private" |sed "s/).*//g" |cut -d '(' -f 1 |tr -d "\t" |tr -s " " |cut -d ' ' -f 3 |grep -v setImageLib |grep -v getTestDataContext |sort |uniq)
        #echo functions Used in ths class are : $functionsInClass
        for function in $functionsInClass
        do
            if echo $func_data |grep -q $function
             then
                #echo "Found function used as : $(echo $func_data |grep -o $function --color)"
                 used_func_data=$(sed -n "/$function/,/throws /{/$function/d;/throws /d;p;}" $className)
                 testDataAttributesInClass=$(getTestDataAttributesInClass $className)
                 dataAttribsInFunction=""$dataAttribsInFunction"
                 $(getListOfUsedAttributesInFunction "$testDataAttributesInClass" "$used_func_data")"
                fi
                done
                # Sanitize : remove empty lines and
                export dataAttribsInFunction=$(echo "$dataAttribsInFunction" |tr -s " " |grep [a-z].* | sed s/"^ "//g |sort | uniq)
                #export dataAttribsInFunction=$(awk 'BEGIN{RS=ORS=" "}!a[$0]++' <<< "$dataAttribsInFunction" |tr -s " " |grep [a-z].* | sed s/"^ "//g|uniq);

        #echo -ne Final data is : \\n"$dataAttribsInFunction"
        getSampleTestData "$dataAttribsInFunction"
        #Data Collected, Start Printing
        echo Class is : $className >> $documentFile
        classNameDisplay=$(basename $className |sed s/.java//g)
        classNameFancy=$(getCanonicalName $classNameDisplay |sed "s/ Helper//g")
        methodNameFancy=$(getCanonicalName $methodName)
        if [ "$classNameDisplay" != "$oldClassName" ]
        then
            let classCounter=$classCounter+1
            let methodCounter=0;
            # Write class Name
            cat << EOM >> $htmlDocFile
            <div class="Properties" style="width:95%;"> <a href="#top">Back to top</a> </div>
            <hr size="1" width="95%" align="left">
            <h2 class="queryselect">${classCounter}. $classNameDisplay</h2>
             <p> This class contains all the $classNameFancy related methods </p>
<h4>Initialization</h4>
<div class="code">$classNameDisplay helper = new $classNameDisplay(dataProperty,"testcaseID");
</div>
<h4>Sample</h4>
<div class="code">$classNameDisplay helper = new $classNameDisplay(${classNameDisplay}DataProperty,$((1000 + RANDOM % 9999)));
</div>
EOM
            fi

        oldClassName=$classNameDisplay
        let methodCounter=$methodCounter+1
        # Write Method
        echo Method is : $methodName >> $documentFile
        cat << EOM >> $htmlDocFile
        <h3 class="queryselect">${classCounter}.${methodCounter} $methodName</h3>
<!-- whatever other content, probably some paragraphs and stuff. -->
<p> This method is used to $methodNameFancy </p>
<h4>Usage:</h4>
<!-- whatever other content, probably some paragraphs and stuff. -->
<div class="code">$classNameDisplay helper = new $classNameDisplay(dataProperty,"testcaseID");
helper.$methodName();
</div>
EOM
if echo $dataAttribsInFunction |grep -q [a-z].*
then
    cat << EOM >> $htmlDocFile
<h4>Test Data Attribs</h4>
<div class="code">$dataAttribsInFunction
</div>
EOM
fi

if echo $dataAttribsInFunction |grep -q [a-z].*
then
    cat << EOM >> $htmlDocFile
<h4>Sample Test Data</h4>
<div class="code">$dataSampleInFunction
</div>
EOM
fi

        echo data attribs : $dataAttribsInFunction >> $documentFile
        echo "" >> $documentFile
        echo -ne "\n\n\n\033[0m"
    done < $mapFile
    echo -ne "\n\n\n"
    #echo -ne "\nClass Method Helper Object Method\n"
    #echo -ne Map is : "$map\n"
    exit
}

initializeHtmlFile()
{
cat <<EOC > $htmlDocFile
<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title>GTA Framework Guide</title>

	<script>
	function htmlTableOfContentsClass (documentRef) {
    var documentRef = documentRef || document;
    var toc = documentRef.getElementById('toc');
    var headings = [].slice.call(documentRef.body.querySelectorAll('h2.queryselect, h3.queryselect, h4.queryselect, h5.queryselect, h6.queryselect'));
    headings.forEach(function (heading, index) {
        var anchor = documentRef.createElement('a');
        anchor.setAttribute('name', 'id_anchor' + index);
        anchor.setAttribute('id', 'id_anchor' + index);

        var link = documentRef.createElement('a');
        link.setAttribute('href', '#id_anchor' + index);
        link.textContent = heading.textContent;

        var div = documentRef.createElement('div');
        div.setAttribute('class', heading.tagName.toLowerCase());

        div.appendChild(link);
        toc.appendChild(div);
        heading.parentNode.insertBefore(anchor, heading);
    });
}

try {
     module.exports = htmlTableOfContents;
} catch (e) {
    // module.exports is not defined
}

</script>

	<style type="text/css">
#toc .h1 {
    text-indent: .25in;
            font-size: 15px;


}
#toc .h2 {
    text-indent: .5in;
            font-size: 17px;
            padding-top: 8px;"


}
a {
           text-decoration: none;
}
#toc .h3 {
    text-indent: .75in;
            font-size: 15px;
            padding-top: 2px;"

}
#toc .h4 {
    text-indent: 1in;
            font-size: 15px;

}
#toc .h5 {
    text-indent: 1.25in;
}
#toc .h6 {
    text-indent: 1.5in;
}
body {
    font: normal 68% verdana, arial, helvetica;
    font-size: 15px;
    color: #000000;
}
table tr td,
table tr th {
    font-size: 68%;
}
table.details tr th {
    font-weight: bold;
    text-align: left;
    background: #a6caf0;
}
table.details tr td {
    background: #eeeee0;
}
p {
    font: normal 68% verdana, arial, helvetica;
    line-height: 1.5em;
    margin-top: 0.5em;
    margin-bottom: 1.0em;
    font-size: 15px;
    max-width: 740px;
}


h1 {
    margin: 0px 0px 5px;
    font: bold 165% verdana, arial, helvetica;
    color: #F07D00;
}
h2 {
    margin-top: 1em;
    margin-bottom: 0.5em;
    font: bold 145% verdana, arial, helvetica;
    color: #F07D00;
}
h3 {
    margin-bottom: 0.5em;
    font: bold 105% verdana, arial, helvetica;
        color: #F07D00;
}
h4 {
    margin-bottom: 0.5em;
    font: bold 90% verdana, arial, helvetica;
            color: #484848;
            text-decoration: underline;

}
h5 {
    margin-bottom: 0.5em;
    font: bold 100% verdana, arial, helvetica
}
h6 {
    margin-bottom: 0.5em;
    font: bold 100% verdana, arial, helvetica
}
.Properties {
    text-align: right;
}
.code {
background-color: #eeeee0;
font-family: Monaco, Consolas, "Andale Mono", "DejaVu Sans Mono", monospace;
font-family: 'Source Code Pro';
    background: #eeeee0;
    font-family: 'Source Code Pro';
    padding-left: 30px;
    padding-right: 30px;
    padding-top: 15px;
    padding-bottom: 15px;
    font-size: 16px;
    color: #484848;
    white-space: pre;
    border-radius: 8px;
    display: inline-block;
}

	</style>

</HEAD>
<body onload="htmlTableOfContentsClass();">
<br/>
<br/>
<h1 style="text-align: center;">GTA Framework Guide</h1>
<br/>
<!--<hr size="1" width="95%" align="left">-->
<!-- <img src="pic_mountain.jpg" alt="Mountain View" style="width:304px;height:228px;"> -->
<h1>Framework Architecture</h1>
<hr size="1" width="95%" align="left">
<br/>
<br/>
<br/>
<img src="GTAFrameworkArchitecture.png" alt="Design" align="center" style="width:85%;height:80%;padding-left: 30px;">
<br/>
<br/>
<br/>
<h1>Guide to Writing tests</h1>
<hr size="1" width="95%" align="left">

<h3>Test Suites</h3>
<p> Tests are organized in following structure of packages & classes</p>
<h4>Test Suite Class </h4>
<div class="code">src/&lt;FeatureName&gt;/&lt;/TestSuiteName&gt;.java </div>

<h4>Test Suite Sample </h4>

<div class="code">src/testsuites/Authentication/SignInTest.java
</div>
<h3>Test Case</h3>
<p>Testcases are written as simple java methods using '@Test' annotation of testNG. </p>
<h4>Testcase Syntax: </h4>
<div class="code">@Test
public void test-description_testcaseId() throws Exception
{
    &lt;UtilityHelper&gt; object = new &lt;UtilityHelper&gt; (dataProperty,"testcaseID");
    object.method();
}
</div>
<h4>Testcase Sample: </h4>
<div class="code">@Test
public void signUpFrontend_1145() throws Exception
{
    AuthenticationHelper helper = new AuthenticationHelper(dataProperty,"1145");
    helper.signup();
}    </div>
<h3>Test Data</h3>
<p>Test data is equivalent of property files where key value pairs are stored.</p>
<h4>Test Data Location: </h4>
<div class="code">Location:
    &lt;testdata&gt;/&lt;functionality&gt/&lt;/featurename.txt&gt;
Sample:
    testdata/authentication/signup.txt</div>

<h4>Test Data Syntax: </h4>
    <div class="code">&lt;testcaseId&gt;_parameter1=value1
&lt;testcaseId&gt;_parameter1=value1
....</div>

<h4>Test Data Sample: </h4>
<div class
1145_password=test
....
</div>
<br/>
<br/>
<a name="top"></a>

<!--<hr size="1" width="95%" align="left">-->
<br/>

<h1 class="queryselect">Utilities</h1>
<hr size="1" width="95%" align="left">

<br/>
<p>Here is a list of utilities, their methods and details of using with test data</p>
<div id="toc"> </div>
EOC
}

concludeHmlFile()
{
  cat <<EOC >> $htmlDocFile
</body>
</HTML>
EOC
}

initializeHtmlFile
updateDoc
concludeHmlFile
$@
