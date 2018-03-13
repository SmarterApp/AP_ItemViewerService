package org.smarterbalanced.itemviewerservice.core.DiagnosticApi.Models;

import java.util.HashMap;
import java.util.Map;

public final class AccommodationDefaultTypeLookup {

    private static final Map<String, String> defaultAccommodations;

    static  {
        defaultAccommodations = new HashMap<String,String>();

        //Default Codes
        defaultAccommodations.put("TDS_ITM1", "Item Tools Menu");
        defaultAccommodations.put("TDS_APC_SCRUBBER", "Audio Playback Controls");
        defaultAccommodations.put("TDS_APC_PSP", "Audio Playback Controls");
        defaultAccommodations.put("TDS_T1", "Tutorial");
        defaultAccommodations.put("TDS_F_S14", "Passage Font Size");
        defaultAccommodations.put("TDS_FT_Verdana", "Font Type");
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

    public static Map<String,String> getDefaultTypes(){
        return defaultAccommodations;
    }
}
