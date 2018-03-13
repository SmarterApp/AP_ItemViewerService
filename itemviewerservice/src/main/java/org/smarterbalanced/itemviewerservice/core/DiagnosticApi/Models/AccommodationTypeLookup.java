package org.smarterbalanced.itemviewerservice.core.DiagnosticApi.Models;


import java.util.HashMap;
import java.util.Map;

/* This has a manually built map of all of the accommodations.
   The itemrenderer package has some lookup methods but none of them contain all of the codes,
   and none of them go from code to type.
   The spec for this project requires us to take in only codes, but the system requires a type
   and a code.
   This maps the code to the type it originates from.*/

/**
 * Type that contains a reverse mapping of accommodation codes to types.
 */
public final class AccommodationTypeLookup {
  private static final Map<String, String> accommodationMap;
  private static final Map<String, String> defaultAccommodations;

  static {
    accommodationMap = new HashMap<String, String>();
    defaultAccommodations = new HashMap<String,String>();

    //American Sign Language
    accommodationMap.put("TDS_ASL0", "American Sign Language");
    accommodationMap.put("TDS_ASL1", "American Sign Language");

    //Audio Settings
    accommodationMap.put("TDS_APC_PSP", "Audio Playback Controls");
    accommodationMap.put("TDS_APC_SCRUBBER", "Audio Playback Controls");

    //Braille
    accommodationMap.put("TDS_BT0", "BrailleType");
    accommodationMap.put("TDS_BT_EXN", "BrailleType");
    accommodationMap.put("TDS_BT_ECN", "BrailleType");
    accommodationMap.put("TDS_BT_UXN", "BrailleType");
    accommodationMap.put("TDS_BT_UCN", "BrailleType");
    accommodationMap.put("TDS_BT_UXT", "BrailleType");
    accommodationMap.put("TDS_BT_UCT", "BrailleType");
    accommodationMap.put("TDS_BT_ECL", "BrailleType");
    accommodationMap.put("TDS_BT_EXL", "BrailleType");


    //Closed Captioning
    accommodationMap.put("TDS_ClosedCap0", "Closed Captioning");
    accommodationMap.put("TDS_ClosedCap1", "Closed Captioning");

    //Color Contrast
    accommodationMap.put("TDS_CC0", "ColorContrast");
    accommodationMap.put("TDS_CCInvert", "ColorContrast");
    accommodationMap.put("TDS_CCMagenta", "ColorContrast");
    accommodationMap.put("TDS_CCMedGrayLtGray", "ColorContrast");
    accommodationMap.put("TDS_CCYellowB", "ColorContrast");

    //Highlight
    accommodationMap.put("TDS_Highlight0", "Highlight");
    accommodationMap.put("TDS_Highlight1", "Highlight");

    //Illustration Glossary
    accommodationMap.put("TDS_ILG0", "Illustration Glossary");
    accommodationMap.put("TDS_ILG1", "Illustration Glossary");

    //Language
    accommodationMap.put("ENU", "Language");
    accommodationMap.put("ESN", "Language");
    accommodationMap.put("ENU-Braille", "Language");

    //Mark for Review
    accommodationMap.put("TDS_MfR1", "Mark for Review");
    accommodationMap.put("TDS_MfR0", "Mark for Review");

    //Masking
    accommodationMap.put("TDS_Masking0", "Masking");
    accommodationMap.put("TDS_Masking1", "Masking");

    //Student Comment notepad
    accommodationMap.put("TDS_SCNotepad", "Student Comments");
    accommodationMap.put("TDS_SC0", "Student Comments");

    //Permissive Mode
    accommodationMap.put("TDS_PM0", "Permissive Mode");
    accommodationMap.put("TDS_PM1", "Permissive Mode");

    //Print on Demand
    accommodationMap.put("TDS_PoD0", "PrintOnDemand");
    accommodationMap.put("TDS_PoD_Stim", "PrintOnDemand");
    accommodationMap.put("TDS_PoD_Item", "PrintOnDemand");
    accommodationMap.put("TDS_PoD_Stim&TDS_PoD_Item", "PrintOnDemand");

    //Print Size
    accommodationMap.put("TDS_PS_L0", "Print Size");
    accommodationMap.put("TDS_PS_L1", "Print Size");
    accommodationMap.put("TDS_PS_L2", "Print Size");
    accommodationMap.put("TDS_PS_L3", "Print Size");
    accommodationMap.put("TDS_PS_L4", "Print Size");

    //Streamlined Interface
    accommodationMap.put("TDS_SLM1", "Streamlined Mode");
    accommodationMap.put("TDS_TS_Modern", "Streamlined Mode");
    accommodationMap.put("TDS_SLM0", "Streamlined Mode");
    accommodationMap.put("TDS_TS_Accessibility", "Streamlined Mode");

    //Strikethrough
    accommodationMap.put("TDS_ST0", "Strikethrough");
    accommodationMap.put("TDS_ST1", "Strikethrough");

    //System Volume Control
    accommodationMap.put("TDS_SVC1", "System Volume Control");

    //Text to Speech
    accommodationMap.put("TDS_TTS0", "TTS");
    accommodationMap.put("TDS_TTS_Item", "TTS");
    accommodationMap.put("TDS_TTS_Stim", "TTS");
    accommodationMap.put("TDS_TTS_Stim&TDS_TTS_Item", "TTS");

    //Translation Glossary
    accommodationMap.put("TDS_WL0", "Word List");
    accommodationMap.put("TDS_WL_Glossary", "Word List");
    accommodationMap.put("TDS_WL_ArabicGloss", "Word List");
    accommodationMap.put("TDS_WL_CantoneseGloss", "Word List");
    accommodationMap.put("TDS_WL_ESNGlossary", "Word List");
    accommodationMap.put("TDS_WL_KoreanGloss", "Word List");
    accommodationMap.put("TDS_WL_MandarinGloss", "Word List");
    accommodationMap.put("TDS_WL_PunjabiGloss", "Word List");
    accommodationMap.put("TDS_WL_RussianGloss", "Word List");
    accommodationMap.put("TDS_WL_TagalGloss", "Word List");
    accommodationMap.put("TDS_WL_UkrainianGloss", "Word List");
    accommodationMap.put("TDS_WL_VietnameseGloss", "Word List");
    accommodationMap.put("TDS_WL_ArabicGloss&TDS_WL_Glossary", "Word List");
    accommodationMap.put("TDS_WL_CantoneseGloss&TDS_WL_Glossary", "Word List");
    accommodationMap.put("TDS_WL_ESNGlossary&TDS_WL_Glossary", "Word List");
    accommodationMap.put("TDS_WL_KoreanGloss&TDS_WL_Glossary", "Word List");
    accommodationMap.put("TDS_WL_MandarinGloss&TDS_WL_Glossary", "Word List");
    accommodationMap.put("TDS_WL_PunjabiGloss&TDS_WL_Glossary", "Word List");
    accommodationMap.put("TDS_WL_RussianGloss&TDS_WL_Glossary", "Word List");
    accommodationMap.put("TDS_WL_TagalGloss&TDS_WL_Glossary", "Word List");
    accommodationMap.put("TDS_WL_UkrainianGloss&TDS_WL_Glossary", "Word List");
    accommodationMap.put("TDS_WL_VietnameseGloss&TDS_WL_Glossary", "Word List");
    accommodationMap.put("TDS_WL_Illustration", "Word List");

    //Calculator
    accommodationMap.put("TDS_Calc0", "Calculator");
    accommodationMap.put("TDS_CalcSciInv", "Calculator");
    accommodationMap.put("Tds_CalcGraphingInv", "Calculator");
    accommodationMap.put("TDS_CalcRegress", "Calculator");
    accommodationMap.put("TDS_CalcSciInv&TDS_CalcGraphingInv&TDS_CalcRegress", "Calculator");
    accommodationMap.put("TDS_CalcBasic", "Calculator");


    //Dictionary and Thesaurus
    accommodationMap.put("TDS_Dict0", "Dictionary");
    accommodationMap.put("TDS_Dict_SD2", "Dictionary");
    accommodationMap.put("TDS_Dict_SD3", "Dictionary");
    accommodationMap.put("TDS_Dict_SD4", "Dictionary");
    accommodationMap.put("TDS_TH0", "Thesaurus");
    accommodationMap.put("TDS_TH_TA", "Thesaurus");
    accommodationMap.put("TDS_TO_All", "Thesaurus");

    //Expandable Passages
    accommodationMap.put("TDS_ExpandablePassages0", "Expandable Passages");
    accommodationMap.put("TDS_ExpandablePassages1", "Expandable Passages");

    //Font Type
    accommodationMap.put("TDS_FT_Serif", "Font Type");
    accommodationMap.put("TDS_FT_Verdana", "Font Type");

    //Global Notes
    accommodationMap.put("TDS_GN0", "Global Notes");
    accommodationMap.put("TDS_GN1", "Global Notes");

    //Item Font Size
    accommodationMap.put("TDS_IF_S14", "Item Font Size");

    //Item Tools Menu
    accommodationMap.put("TDS_ITM0", "Item Tools Menu");
    accommodationMap.put("TDS_ITM1", "Item Tools Menu");

    //Passage Font Size
    accommodationMap.put("TDS_F_S14", "Passage Font Size");

    //Mute System Volume
    accommodationMap.put("TDS_Mute0", "Mute System Volume");
    accommodationMap.put("TDS_Mute1", "Mute System Volume");

    //Tutorial
    accommodationMap.put("TDS_T0", "Tutorial");
    accommodationMap.put("TDS_T1", "Tutorial");

  }

  /**
   * Looks up the type for a given code.
   *
   * @param code the code that is being looked up.
   * @return the type that the code belongs to.
   */
  public static String getType(String code) {
    return accommodationMap.get(code);
  }

}