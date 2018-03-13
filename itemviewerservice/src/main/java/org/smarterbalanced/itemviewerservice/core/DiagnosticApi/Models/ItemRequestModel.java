package org.smarterbalanced.itemviewerservice.core.DiagnosticApi.Models;

import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Models an item request.
 */
public class ItemRequestModel {

  private final String[] items;
  private final String[] featureCodes;
  private final String section;
  private final String revision;
  private List<AccommodationModel> accommodations;
  private final String loadFrom;
  private static final Logger logger = LoggerFactory.getLogger(ItemRequestModel.class);

  /**
   * Instantiates a new Item model.
   *
   * @param items         The items requested
   * @param featureCodes Accessibility feature codes
   */
  public ItemRequestModel(String[] items, String[] featureCodes, String section, String revision, String loadFrom) {
    this.items = items;
    this.featureCodes = featureCodes;
    this.accommodations = new ArrayList<>();
    this.loadFrom = loadFrom;
    this.revision = revision;
    this.section = section;
  }

  private void buildAccommodations() {
    HashMap<String, List<String>> accomms = new HashMap<>();
    for (String code: this.featureCodes) {
      if (StringUtils.isNullOrEmpty(code)) {
        continue;
      }

      String type = AccommodationTypeLookup.getType(code);
      //If type is null then the accommodation is not found. Do not add it to the list.
      if (type != null) {
        if (accomms.containsKey(type)) {
          List<String> accomCodes = accomms.get(type);
          accomCodes.add(code);
          accomms.put(type, accomCodes);
        } else {
          List<String> accomCodes = new ArrayList<String>();
          accomCodes.add(code);
          accomms.put(type, accomCodes);
        }
      } else {
        logger.info("Unknown accommodation code requested for item " + this.items[0] + " code: "
                + code);
      }
    }

    for (Map.Entry<String, List<String>> entry: accomms.entrySet()) {
      String type = entry.getKey();
      List<String> codes = entry.getValue();
      AccommodationModel accommodation = new AccommodationModel(type, codes);
      this.accommodations.add(accommodation);
    }
  }

  private void checkIsaapCodes(){
      for (Map.Entry<String, String> entry : AccommodationDefaultTypeLookup.getDefaultTypes().entrySet()) {
        if(!ArrayUtils.contains(this.featureCodes, entry.getKey())){
            String codes = entry.getValue();
            List<String> types = Collections.singletonList(entry.getKey());
            AccommodationModel accommodation = new AccommodationModel(codes, types);
            this.accommodations.add(accommodation);
        }
      }
  }

  /**
   * Generate a json representation of the requested item and accommodations.
   * The token is in the format taken by the blackbox javascript.
   *
   * @return the Json token as a string
   */
  public String generateJsonToken() {
    ObjectMapper mapper = new ObjectMapper();
    buildAccommodations();
    checkIsaapCodes();
    String json;
    TokenModel token = new TokenModel(this.items, this.accommodations, this.loadFrom);
    try {
      json = mapper.writer().writeValueAsString(token);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return "";
    }
    return json;
  }
}
