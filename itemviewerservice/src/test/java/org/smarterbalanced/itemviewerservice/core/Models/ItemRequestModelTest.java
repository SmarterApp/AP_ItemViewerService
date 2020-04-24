package org.smarterbalanced.itemviewerservice.core.Models;

//import com.sun.org.apache.xpath.internal.operations.Equals;
import org.junit.Before;
import org.junit.Test;
import tds.irisshared.models.ItemRequestModel;

//import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

//import static junit.framework.Assert.assertTrue;

public class ItemRequestModelTest {

    ItemRequestModel itemReqNoDefaults;
    ItemRequestModel itemReqNoTTS;
    ItemRequestModel itemReqDefaults;
    ItemRequestModel itemReqTTS;


    @Before
    public void setUp() {
        String items[] = {"182-1432"};
        ArrayList<String> codes = new ArrayList<>();
        ArrayList<String> codesTTS = new ArrayList<>(Arrays.asList("TDS_TTS_Stim"));
        ArrayList<String> codesDefault = new ArrayList<>(Arrays.asList("TDS_ITM1", "TDS_APC_SCRUBBER"));
        itemReqNoDefaults = new ItemRequestModel(items, codes, "");
        itemReqNoTTS = new ItemRequestModel(items, codes, "");
        itemReqTTS = new ItemRequestModel(items, codesTTS, "");
        itemReqDefaults = new ItemRequestModel(items, codesDefault, "");

    }

    @Test
    public void testNoDefaultIsaap() {
        String itemJson = itemReqNoDefaults.generateJsonToken();
        assert(itemJson.contains("TDS_ITM1"));
        assert(itemJson.contains("TDS_APC_SCRUBBER"));
        assert(itemJson.contains("TDS_APC_PSP"));
        assert(itemJson.contains("TDS_T1"));
        assert(itemJson.contains("TDS_F_S14"));
        assert(itemJson.contains("TDS_FT_Verdana"));
    }

    @Test
    public void testDefaultIsaap() {
        String itemJson = itemReqDefaults.generateJsonToken();
        assert(itemJson.contains("TDS_ITM1"));
        assert(itemJson.contains("TDS_APC_SCRUBBER"));
        assert(itemJson.contains("TDS_APC_PSP"));
        assert(itemJson.contains("TDS_T1"));
        assert(itemJson.contains("TDS_F_S14"));
        assert(itemJson.contains("TDS_FT_Verdana"));
    }

    @Test
    public void testTTSIsaap() {
        String itemJson = itemReqTTS.generateJsonToken();
        assert(itemJson.contains("TDS_TTSAA_Volume"));
        assert(itemJson.contains("TDS_TTSAA_Pitch"));
        assert(itemJson.contains("TDS_TTSAA_Rate"));
        assert(itemJson.contains("TDS_TTSAA_SelectVP"));
        assert(itemJson.contains("TDS_TTX_A203"));
        assert(itemJson.contains("TDS_TTSPause1"));
    }

    @Test
    public void testNoTTSIsaap() {
        String itemJson = itemReqNoTTS.generateJsonToken();
        assert(!itemJson.contains("TDS_TTSAA_Volume"));
        assert(!itemJson.contains("TDS_TTSAA_Pitch"));
        assert(!itemJson.contains("TDS_TTSAA_Rate"));
    }
}
