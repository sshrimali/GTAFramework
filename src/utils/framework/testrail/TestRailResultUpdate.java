package utils.framework.testrail;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sshrimali on 1/10/17.
 */
public class TestRailResultUpdate {


    private static String testcaseId;
    private static String result;
    private static String message;
    private static Node resultNode;
    private static String testRailUserName="";
    private static String testRailPassword="";
    private static String testRailURL="";
    private static APIClient client;
    private static String status_id;
    private static String testRunId="";
    private String runId="";
    private static String resultFilePath="reports/TESTS-TestSuites.xml";
    static List<String> testcasesInRun = new ArrayList<String>();
    static String resultDetailSet;
    static HashMap<String, String> resultElement = new HashMap<String,String>();
    static JSONArray resultArray = new JSONArray();




    public static void main(String[] args) throws Exception
    {
        if (!args[0].equalsIgnoreCase("default"))
        {
            testRunId=args[0];
        }
        else
        {
            System.out.println("No test run ID was specified, not updating results in testrail");
            return;
        }
        initializeClient();
        getTestsInRun();
        readResults();
        updateResults();


    }

    private static void updateResults() throws IOException, APIException {

        JSONObject resultRequest = new JSONObject();
        resultRequest.put("results",resultArray);
        JSONArray r = (JSONArray) client.sendPost("add_results_for_cases/" + testRunId, resultRequest);
        System.out.println("Updated results successfully");
    }

    private static void readResults() throws Exception{
        File resultFile = new File(resultFilePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(resultFile);
        doc.getDocumentElement().normalize();
        NodeList nodes = doc.getElementsByTagName("testcase");
        for (int iterator = 0;iterator<nodes.getLength();iterator++)
        {
            testcaseId=nodes.item(iterator).getAttributes().getNamedItem("name").getTextContent().split("_")[1];
            if (!testcasesInRun.contains(testcaseId))
            {
                continue;
            }
            message="Passed in automated execution";
            if (nodes.item(iterator).getChildNodes().getLength() > 0)
            {
                resultNode=nodes.item(iterator).getChildNodes().item(1);
                status_id="5"; //Mark Fail
                message=resultNode.getAttributes().getNamedItem("type").getTextContent() + " : " +resultNode.getAttributes().getNamedItem("message").getTextContent();
            }
            else
            {
                status_id="1"; //Mark Pass
            }
            JSONObject resultElement = new JSONObject();

            resultElement.put("case_id", testcaseId);
            resultElement.put("status_id", status_id);
            resultElement.put("comment", message);
            resultArray.add(resultElement);
        }
    }

    private static void getTestsInRun() throws Exception {
        JSONArray c = (JSONArray) client.sendGet("get_tests/" + testRunId);

        for (int iterator =0; iterator<c.size();iterator++)
        {
            JSONObject obj= (JSONObject) c.get(iterator);
            testcasesInRun.add(obj.get("case_id").toString());
        }
    }

    public static void initializeClient()
    {
        client = new APIClient(testRailURL);
        client.setUser(testRailUserName);
        client.setPassword(testRailPassword);
    }
}
