<xsl:stylesheet	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" indent="yes" encoding="US-ASCII"/>
	<xsl:decimal-format decimal-separator="." grouping-separator="," />
	<!--
     The Apache Software License, Version 1.1

     Copyright (c) 2001-2002 The Apache Software Foundation.  All rights
     reserved.

     Redistribution and use in source and binary forms, with or without
     modification, are permitted provided that the following conditions
     are met:

     1. Redistributions of source code must retain the above copyright
        notice, this list of conditions and the following disclaimer.

     2. Redistributions in binary form must reproduce the above copyright
        notice, this list of conditions and the following disclaimer in
        the documentation and/or other materials provided with the
        distribution.

     3. The end-user documentation included with the redistribution, if
        any, must include the following acknowlegement:
           "This product includes software developed by the
            Apache Software Foundation (http://www.apache.org/)."
        Alternately, this acknowlegement may appear in the software itself,
        if and wherever such third-party acknowlegements normally appear.

     4. The names "The Jakarta Project", "Ant", and "Apache Software
        Foundation" must not be used to endorse or promote products derived
        from this software without prior written permission. For written
        permission, please contact apache@apache.org.

     5. Products derived from this software may not be called "Apache"
        nor may "Apache" appear in their names without prior written
        permission of the Apache Group.

     THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
     WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
     OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
     DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
     ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
     SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
     LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
     USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
     ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
     OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
     OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
     SUCH DAMAGE.
     ====================================================================

     This software consists of voluntary contributions made by many
     individuals on behalf of the Apache Software Foundation.  For more
     information on the Apache Software Foundation, please see
     <http://www.apache.org/>.
     -->

	<!--



     It creates a non-framed report that can be useful to send via
     e-mail or such.

     @author Stephane Bailliez <a href="mailto:sbailliez@apache.org"/>
     @author Erik Hatcher <a href="mailto:ehatcher@apache.org"/>

    -->
<xsl:template match="testsuites">
		<HTML>
			<HEAD>
				<title>GTA Test framework report</title>
				<script src="https://code.jquery.com/jquery-1.9.1.min.js">test </script>
				<script src="https://www.amcharts.com/lib/3/amcharts.js"> test</script>
				<script src="https://www.amcharts.com/lib/3/pie.js"> test </script>
				<script src="https://www.amcharts.com/lib/3/themes/light.js"> test </script>
				<script type="text/javascript">

					$(document).ready(function()
					{
					var listSize = document.getElementsByClassName("graphSuiteTotal").length;
					var result1 = document.getElementsByClassName("graphSuiteFailure")[1].innerHTML;

					var totalFailures=0;
					var data=[];
					for (var iterator=0;iterator<xsl:text disable-output-escaping="yes">&lt;</xsl:text>listSize;iterator++)
					{
						var suiteName=document.getElementsByClassName("graphSuiteName")[iterator].innerHTML;
						var failures=document.getElementsByClassName("graphSuiteFailure")[iterator].innerHTML;
						totalFailures=+totalFailures + +document.getElementsByClassName("graphSuiteFailure")[iterator].innerHTML;

						var stat= {suiteName: suiteName,failures: failures};
						if (failures!="0")
						{
							data.push(stat);
						}
					}
					console.log("Data1 : " + JSON.stringify(data));
					console.log("Data2 : " + data);

					var chart = AmCharts.makeChart( "chartdiv", {
					"type": "pie",
					 "legend":{
					"position":"right",
					"marginRight":400,
					"autoMargins":false
					},
					"setLegendPosition" : "top center",
					"bezierX": 1,
					"theme": "light",
					"legends": "true",
					"dataProvider": data,
					"valueField": "failures",
					"titleField": "suiteName",
					"balloon":{
					"fixedPosition":true
					},
					"export": {
					"enabled": true
					}
					} );

					});
				</script>
				<style type="text/css">
					#chartdiv {
					width		: 120%;
					height		: 400px;
					font-size	: 10px;
					}
					body {
					font:normal 68% verdana,arial,helvetica;
					color:#000000;
					}
					table tr td, table tr th {
					font-size: 68%;
					}
					table.details tr th{
					font-weight: bold;
					text-align:left;
					background:#a6caf0;
					}
					table.details tr td{
					background:#eeeee0;
					}

					p {
					line-height:1.5em;
					margin-top:0.5em; margin-bottom:1.0em;
					}
					h1 {
					margin: 0px 0px 5px; font: 165% verdana,arial,helvetica
					}
					h2 {
					margin-top: 1em; margin-bottom: 0.5em; font: bold 125% verdana,arial,helvetica
					}
					h3 {
					margin-bottom: 0.5em; font: bold 115% verdana,arial,helvetica
					}
					h4 {
					margin-bottom: 0.5em; font: bold 100% verdana,arial,helvetica
					}
					h5 {
					margin-bottom: 0.5em; font: bold 100% verdana,arial,helvetica
					}
					h6 {
					margin-bottom: 0.5em; font: bold 100% verdana,arial,helvetica
					}
					.Error {
					font-weight:bold; color:purple;
					}
					.Failure {
					font-weight:bold; color:purple;
					}
					.FailDetails {
					font-weight:normal;
					color:purple;
					}
					.Properties {
					text-align:right;
					}
					.tooltip {
					transform: none;
					margin: 5px;
					}

					.tooltip:hover <xsl:text disable-output-escaping="yes">&gt;</xsl:text> .tooltip-text {
					pointer-events: auto;
					opacity: 1.0;
					}

					.tooltip <xsl:text disable-output-escaping="yes">&gt;</xsl:text> .tooltip-text {
					display: block;
					position: absolute;
					left: 640px;
					z-index: 2000;
					overflow: visible;
					padding: 5px 8px;
					margin-top: 10px;
					line-height: 16px;
					border-radius: 8px;
					text-align: left;
					color: #fff;
					color: #a6caf0;
					background: #a6caf0;
					pointer-events: none;
					opacity: 0.0;
					-o-transition: all 1.5s ease-out;
					-ms-transition: all 1.5s ease-out;
					-moz-transition: all 1.5s ease-out;
					-webkit-transition: all 1.5s ease-out;
					transition: all 1.5s ease-out;
					}

					/* Arrow */
					.tooltip <xsl:text disable-output-escaping="yes">&gt;</xsl:text> .tooltip-text:before {
					display: inline;
					top: -5px;
					content: "";

					position: absolute;
					border: solid;
					border-color: rgba(0, 0, 0, 1) transparent;
					border-width: 1 .5em .5em .5em;
					z-index: 6000;
					left: 455px;
					}

					/* Invisible area so you can hover over tooltip */
					.tooltip <xsl:text disable-output-escaping="yes">&gt;</xsl:text> .tooltip-text:after {
					top: -20px;
					content: " ";
					display: block;
					height: 20px;
					position: absolute;
					width: 150px;
					left: 400px;
					}

					.tooltip-scroll {
					overflow-y: auto;
					max-height: 300px;
					}

				</style>

			</HEAD>
			<body>
				<a name="top"></a>
				<xsl:call-template name="pageHeader"/>

				<!-- Summary part -->
				<xsl:call-template name="summary"/>
			<hr size="1" width="95%" align="left"/>

				<!-- Package List part -->
				<xsl:call-template name="packagelist"/>
			<hr size="1" width="95%" align="left"/>

				<!-- For each package create its part -->
				<xsl:call-template name="packages"/>
			<hr size="1" width="95%" align="left"/>

				<!-- For each class create the  part -->
				<xsl:call-template name="classes"/>

			</body>
		</HTML>
	</xsl:template>



	<!-- ================================================================== -->
	<!-- Write a list of all packages with an hyperlink to the anchor of    -->
	<!-- of the package name.                                               -->
	<!-- ================================================================== -->
	<xsl:template name="packagelist">
		<h2>Test Suites</h2>

		<table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">
			<xsl:call-template name="testsuite.test.header"/>
			<!-- list all packages recursively -->
			<xsl:for-each select="./testsuite[not(./@package = preceding-sibling::testsuite/@package)]">
				<xsl:sort select="@package"/>
				<xsl:variable name="testsuites-in-package" select="/testsuites/testsuite[./@package = current()/@package]"/>
				<xsl:variable name="testCount" select="sum($testsuites-in-package/@tests)"/>
				<xsl:variable name="errorCount" select="sum($testsuites-in-package/@errors)"/>
				<xsl:variable name="failureCount" select="(sum($testsuites-in-package/@failures)) + $errorCount"/>
				<xsl:variable name="successCount" select="($testCount - $failureCount)"/>
				<xsl:variable name="timeCount" select="sum($testsuites-in-package/@time)"/>

				<!-- write a summary for the package -->
				<tr valign="top">
					<!-- set a nice color depending if there is an error/failure -->
					<xsl:attribute name="class">
						<xsl:choose>
							<xsl:when test="$failureCount &gt; 0">Failure</xsl:when>
							<xsl:when test="$errorCount &gt; 0">Failure</xsl:when>
						</xsl:choose>
					</xsl:attribute>
					<td><a href="#{@package}" ><xsl:value-of select="substring-after(@package,'.')"/></a></td>
					<td ><xsl:value-of select="$testCount"/></td>
					<td><xsl:value-of select="$successCount"/></td>
					<td><xsl:value-of select="$failureCount"/></td>
					<td>
						<xsl:call-template name="display-time">
							<xsl:with-param name="value" select="$timeCount"/>
						</xsl:call-template>
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>


	<!-- ================================================================== -->
	<!-- Write a package level report                                       -->
	<!-- It creates a table with values from the document:                  -->
	<!-- Name | Tests | Errors | Failures | Time                            -->
	<!-- ================================================================== -->
	<xsl:template name="packages">
		<!-- create an anchor to this package name -->
		<xsl:for-each select="/testsuites/testsuite[not(./@package = preceding-sibling::testsuite/@package)]">
			<xsl:sort select="@package"/>
				<a name="{@package}"></a>
				<h3>Package <xsl:value-of select="substring-after(@package,'.')"/></h3>
				<table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">
				<xsl:call-template name="testsuite.test.header"/>

				<!-- match the testsuites of this package -->
					<xsl:apply-templates select="/testsuites/testsuite[./@package = current()/@package]" mode="print.test"/>
			</table>
			<a href="#top">Back to top</a>
			<p/>
			<p/>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="classes">
		<xsl:for-each select="testsuite">
			<xsl:sort select="@name"/>
			<!-- create an anchor to this class name -->
			<a name="{@name}"></a>
			<h3>Suite <xsl:value-of select="@name"/></h3>

			<table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">
				<xsl:call-template name="testcase.test.header"/>
				<!--
                test can even not be started at all (failure to load the class)
                so report the error directly
                -->
				<xsl:if test="./error">
					<tr class="Error">
						<td colspan="4"><xsl:apply-templates select="./error"/></td>
					</tr>
				</xsl:if>
				<xsl:apply-templates select="./testcase" mode="print.test"/>
			</table>

			<p/>

			<a href="#top">Back to top</a>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="summary">
		<h2>Summary</h2>
		<xsl:variable name="testCount" select="sum(testsuite/@tests)"/>
		<xsl:variable name="errorCount" select="sum(testsuite/@errors)"/>
		<xsl:variable name="failureCount" select="(sum(testsuite/@failures)) + $errorCount"/>
		<xsl:variable name="timeCount" select="sum(testsuite/@time)"/>
		<xsl:variable name="successCount" select="($testCount - $failureCount)"/>
		<xsl:variable name="successRate" select="($testCount - $failureCount) div $testCount"/>
		<table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">
			<tr valign="top">
				<th>Tests</th>
				<th>Success</th>
				<th>Failures</th>
				<th>Success rate</th>
				<th width="150px">Time(H:MM:SS)</th>
			</tr>
			<tr valign="top">
				<xsl:attribute name="class">
					<xsl:choose>
						<xsl:when test="$failureCount &gt; 0">Failure</xsl:when>
						<xsl:when test="$errorCount &gt; 0">Error</xsl:when>
					</xsl:choose>
				</xsl:attribute>
				<td><xsl:value-of select="$testCount"/></td>
				<td><xsl:value-of select="$successCount"/></td>
				<td><xsl:value-of select="$failureCount"/></td>
				<td>
					<xsl:call-template name="display-percent">
						<xsl:with-param name="value" select="$successRate"/>
					</xsl:call-template>
				</td>
				<td>
					<xsl:call-template name="display-time">
						<xsl:with-param name="value" select="$timeCount"/>
					</xsl:call-template>
				</td>

			</tr>
		</table>
		<table border="0" width="95%">
			<tr>
				<td	style="text-align: justify;">
					Note
				</td>
			</tr>
		</table>
	</xsl:template>

	<!--
     Write properties into a JavaScript data structure.
     This is based on the original idea by Erik Hatcher (ehatcher@apache.org)
     -->
	<xsl:template match="properties">
		cur = TestCases['<xsl:value-of select="../@package"/>.<xsl:value-of select="../@name"/>'] = new Array();
		<xsl:for-each select="property">
			<xsl:sort select="@name"/>
			cur['<xsl:value-of select="@name"/>'] = '<xsl:call-template name="JS-escape"><xsl:with-param name="string" select="@value"/></xsl:call-template>';
		</xsl:for-each>
	</xsl:template>

	<!-- Page HEADER -->
	<xsl:template name="pageHeader">

		<h1>GTA test framework Report</h1>
		<hr size="1" width="95%" align="left"/>
		<h2>Graphical Failure Analysis</h2>
		<div id="chartdiv" > <a href="
		<div style="width:95%;" align="left"><p align="right"><a href="videos/user_stories.mp4" target="_blank">Play prime user scenarios video > </a></p> </div>
		<hr size="1" width="95%" align="left"/>
	</xsl:template>

<xsl:template match="testsuite" mode="header">
		<tr valign="top">
		<th width="80%">Name</th>
			<th>Tests</th>
			<th>Success</th>
			<th>Failures</th>
			<th nowrap="nowrap">Time(H:MM:SS)</th>
		</tr>
	</xsl:template>
	<!-- class header -->
	<xsl:template name="testsuite.test.header">
		<tr valign="top">
			<th width="80%">Name</th>
			<th>Tests</th>
			<th>Success</th>
			<th>Failures</th>
			<th nowrap="nowrap">Time(H:MM:SS)</th>
		</tr>
	</xsl:template>

	<!-- method header -->
	<xsl:template name="testcase.test.header">
		<tr valign="top">
			<th>Testcase</th>
			<th width="20%">Name</th>
			<th>Status</th>
			<th width="70%">Details</th>
			<th nowrap="nowrap">Time<br/>(H:MM:SS)</th>
		</tr>
	</xsl:template>


	<!-- class information -->
<xsl:template match="testsuite" mode="print.test">
		<tr valign="top">
			<!-- set a nice color depending if there is an error/failure -->
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@failures[.&gt; 0]">Failure</xsl:when>
					<xsl:when test="@errors[.&gt; 0]">Error</xsl:when>
				</xsl:choose>
			</xsl:attribute>

			<!-- print testsuite information -->


			<xsl:variable name="testCountLocal" select="sum(@tests)"/>
			<xsl:variable name="errorCountLocal" select="sum(@errors)"/>
			<xsl:variable name="failureCountLocal" select="(sum(@failures)) + $errorCountLocal"/>
			<xsl:variable name="successCountLocal" select="($testCountLocal - $failureCountLocal)"/>

			<td><a href="#{@name}" class="graphSuiteName"><xsl:value-of select="@name"/></a></td>
			<td class="graphSuiteTotal"><xsl:value-of select="@tests"/></td>
			<td class="graphSuiteSuccess"><xsl:value-of select="$successCountLocal"/></td>
			<td class="graphSuiteFailure"><xsl:value-of select="$failureCountLocal"/></td>
			<td>
				<xsl:call-template name="display-time">
					<xsl:with-param name="value" select="@time"/>
				</xsl:call-template>
			</td>
		</tr>
	</xsl:template>

<xsl:template match="testcase" mode="print.test">
		<tr valign="top">
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="failure | error">Failure</xsl:when>
				</xsl:choose>
			</xsl:attribute>
			<!--TODO-->
			<xsl:variable name="testID" select="substring-after(@name, '_')"/>
			<td> <a href="
			<td width="50%"><xsl:value-of select="substring-before(@name, '_')"/> </td>
			<xsl:choose>
				<xsl:when test="failure">
					<td>Failure</td>
					<td>
						<xsl:choose>
							<xsl:when test="fs:exists(fs:new(concat('reports/html/screenshots/', $testID, '_actual.png')))" xmlns:fs="java.io.File">
								Image - <a href="./diff.html?test={$testID}" target="_blank"> Actual vs Expected</a><br/>
							</xsl:when>
						</xsl:choose>
						Video - <a href="videos/test_{$testID}_movie.mp4" target="_blank">Play</a>
						<br/><br/><br/><xsl:apply-templates select="failure"/>
					</td>
						<!--<xsl:apply-templates select="error"/></td>-->
				</xsl:when>
				<xsl:when test="error">
					<td>Failure</td>
					<td class="FailureDetails">
						<xsl:choose>
						<xsl:when test="fs:exists(fs:new(concat('reports/html/screenshots/', $testID, '_actual.png')))" xmlns:fs="java.io.File">
							Image - <a href="./diff.html?test={$testID}" target="_blank"> Actual vs Expected</a><br/>
						</xsl:when>
					</xsl:choose>
						Video - <a href="videos/test_{$testID}_movie.mp4" target="_blank">Play</a>
						<br/><br/>
						Error message:
						<br/>
						<xsl:apply-templates select="error"/>
						<div style="float:right" class="tooltip">
							<u>Stack trace</u>
							<span class="tooltip-text">
								<div class="tooltip-scroll">
									<code>
										<font size="3" class="failDetails">
											<xsl:call-template name="br-replace" >
												<xsl:with-param name="word" select="."/>
											</xsl:call-template>
										</font>
									</code>
								</div>
							</span>
						</div>

					</td>

				</xsl:when>
				<xsl:otherwise>
					<td>Success</td>
					<td>
					<xsl:choose>
					<xsl:when test="fs:exists(fs:new(concat('reports/html/screenshots/', $testID, '_actual.png')))" xmlns:fs="java.io.File">
						Image - <a href="./diff.html?test={$testID}" target="_blank"> Actual vs Expected</a><br/>
					</xsl:when>
					</xsl:choose>
						Video - <a href="videos/test_{$testID}_movie.mp4" target="_blank">Play</a>
					</td>

				</xsl:otherwise>
			</xsl:choose>
			<td>
				<xsl:call-template name="display-time">
					<xsl:with-param name="value" select="@time"/>
				</xsl:call-template>
			</td>
		</tr>
	</xsl:template>


	<xsl:template match="failure">
		<xsl:call-template name="display-failures"/>
	</xsl:template>

	<xsl:template match="error">
		<xsl:call-template name="display-failures"/>
	</xsl:template>

	<!-- Style for the error and failure in the tescase template -->
	<xsl:template name="display-failures">
		<xsl:choose>
		<xsl:when test="not(@message)">N/A</xsl:when>
			<xsl:otherwise>
			<xsl:value-of disable-output-escaping="yes" select="@message"/>
			</xsl:otherwise>
		</xsl:choose>
		<!-- display the stacktrace -->
		<code>
		<p/>
		<xsl:call-template name="br-replace">
				<xsl:with-param name="word" select="."/>
			</xsl:call-template>
		</code>
		<!--the later is better but might be problematic for non-21" monitors... -->
		<!--pre><xsl:value-of select="."/></pre-->
	</xsl:template>

	<xsl:template name="JS-escape">
		<xsl:param name="string"/>
		<xsl:choose>
			<xsl:when test="contains($string,&quot;'&quot;)">
				<xsl:value-of select="substring-before($string,&quot;'&quot;)"/>\&apos;<xsl:call-template name="JS-escape">
				<xsl:with-param name="string" select="substring-after($string,&quot;'&quot;)"/>
			</xsl:call-template>
			</xsl:when>
			<xsl:when test="contains($string,'\')">
				<xsl:value-of select="substring-before($string,'\')"/>\\<xsl:call-template name="JS-escape">
				<xsl:with-param name="string" select="substring-after($string,'\')"/>
			</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$string"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>




	<xsl:template name="br-replace">
		<xsl:param name="word"/>
		<xsl:choose>
			<xsl:when test="contains($word, '&#xA;')">
				<xsl:value-of select="substring-before($word, '&#xA;')"/>
				<br/>
				<xsl:call-template name="br-replace">
					<xsl:with-param name="word" select="substring-after($word, '&#xA;')"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$word"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="br-replace1">
		<xsl:param name="word"/>

		<xsl:choose>
			<xsl:when test="contains($word,'ERROR')">

				<xsl:choose>
					<xsl:when test="contains($word,'&#xA;')">
						<xsl:value-of select="substring-before($word,'&#xA;')"/>
						<br/>
						<xsl:call-template name="br-replace">

							<xsl:with-param name="word" select="substring-after($word,'&#xA;')"/>
						</xsl:call-template>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$word"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
		</xsl:choose>

	</xsl:template>

	<xsl:template name="display-time">
		<xsl:param name="value"/>

		<xsl:value-of select="floor($value div 3600)" />

		<xsl:variable name="r" select="$value mod 3600"/>
		<xsl:value-of select="format-number(floor($r div 60), ':00')"/>
		<xsl:value-of select="format-number($r mod 60, ':00')"/>
	</xsl:template>

	<xsl:template name="display-percent">
		<xsl:param name="value"/>
		<xsl:value-of select="format-number($value,'0.00%')"/>
	</xsl:template>

</xsl:stylesheet>

