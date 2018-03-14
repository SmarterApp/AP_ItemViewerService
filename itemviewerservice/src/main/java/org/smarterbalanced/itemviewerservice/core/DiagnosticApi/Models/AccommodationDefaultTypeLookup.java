package org.smarterbalanced.itemviewerservice.core.DiagnosticApi.Models;

import java.util.HashMap;
import java.util.Map;

public final class AccommodationDefaultTypeLookup {

    private static final Map<String, String> defaultAccommodations;
    private static final Map<String, String> conditionalAccommodations;

    static  {
        defaultAccommodations = new HashMap<String,String>();
        conditionalAccommodations = new HashMap<String,String>();

        //Default Codes
        defaultAccommodations.put("TDS_ITM1", "Item Tools Menu");
        defaultAccommodations.put("TDS_APC_SCRUBBER", "Audio Playback Controls");
        defaultAccommodations.put("TDS_APC_PSP", "Audio Playback Controls");
        defaultAccommodations.put("TDS_T1", "Tutorial");
        defaultAccommodations.put("TDS_F_S14", "Passage Font Size");
        defaultAccommodations.put("TDS_FT_Verdana", "Font Type");

        //Conditional Codes
        //Audio TTS Adjustments
        conditionalAccommodations.put("TDS_TTSAA_Volume", "TTS Audio Adjustments");
        conditionalAccommodations.put("TDS_TTSAA_Pitch", "TTS Audio Adjustments");
        conditionalAccommodations.put("TDS_TTSAA_Rate", "TTS Audio Adjustments");
        conditionalAccommodations.put("TDS_TTSAA_SelectVP", "TTS Audio Adjustments");

        // TTS Rules
        conditionalAccommodations.put("TDS_TTX_A203 - TTX", "Business Rules");
        conditionalAccommodations.put("TDS_TTSPause1", "TTS Pausing");
    }

    /**
     * Looks up the type for a given code.
     *
     * @param code the code that is being looked up.
     * @return the type that the code belongs to.
     */
    public static String getDefaultType(String code) {
        return defaultAccommodations.get(code);
    }

    public static Map<String,String> getDefaultTypes() {
        return defaultAccommodations;
    }

    public static Map<String,String> getConditionalTypes() {
        return conditionalAccommodations;
    }
}
