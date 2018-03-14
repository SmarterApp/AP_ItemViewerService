package org.smarterbalanced.itemviewerservice.web.controllers;

import org.smarterbalanced.itemviewerservice.core.DiagnosticApi.Models.ItemRequestModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * REST API controller for rendering items.
 */

@Controller
public class RenderItemController {

  /**
   * Loads a single item.
   *
   * @param itemId             Item bank and item ID separated by a "-"
   * @param accommodationCodes Feature codes delimited by semicolons.
   * @return content object.
   */
  @RequestMapping(value = "/{item:\\d+[-]\\d+}", method = RequestMethod.GET)
  @ResponseBody
  public ModelAndView getContent(@PathVariable("item") String itemId,
                                 @RequestParam(value = "isaap", required = false,
                                         defaultValue = "")
                                         String accommodationCodes,
                                 @RequestParam(value = "section",
                                         required = false,
                                         defaultValue = "")
                                           String section,
                                 @RequestParam(value = "revision",
                                         required = false,
                                         defaultValue = "")
                                           String revision,
                                 @RequestParam(value = "readOnly",
                                         required = false,
                                         defaultValue = "false"
                                 ) boolean readOnly,
                                 @RequestParam(value = "loadFrom",
                                         required =  false,
                                         defaultValue = "")
                                           String loadFrom
  ) {
    //Request is in the format
    ArrayList<String> codes = new ArrayList<>(Arrays.asList(accommodationCodes.split(";")));
    String[] itemArr = {itemId};
    //The item model can take in multiple items.
    // In our case we just need to load 1 item so we place the item requested into an array.
    ItemRequestModel item = new ItemRequestModel(itemArr, codes, revision, section, loadFrom);

    String token = item.generateJsonToken();
    ModelAndView model = new ModelAndView();
    model.setViewName("item");
    model.addObject("readOnly", readOnly);
    model.addObject("token", token);
    model.addObject("item", itemArr[0]);
    return model;
  }

  /**
   * Loads multiple items.
   *
   * @param itemIds            Array of items in the format "ItemBank-ItemID"
   * @param accommodationCodes Feature codes delimited by semicolons.
   * @return content object.
   */
  @RequestMapping(value = "/items", method = RequestMethod.GET)
  @ResponseBody
  public ModelAndView getContent(@RequestParam(value = "ids",
                                          required = true)
                                           String[] itemIds,
                                 @RequestParam(value = "scrollToId",
                                         required = false,
                                         defaultValue = "")
                                         String scrollToId,
                                 @RequestParam(value = "isaap",
                                         required = false,
                                         defaultValue = "")
                                         String accommodationCodes,
                                 @RequestParam(value = "section",
                                         required = false,
                                         defaultValue = "")
                                           String section,
                                 @RequestParam(value = "revision",
                                         required = false,
                                         defaultValue = "")
                                           String revision,
                                 @RequestParam(value = "readOnly",
                                         required = false,
                                         defaultValue = "false"
                                 ) boolean readOnly,
                                 @RequestParam(value = "loadFrom",
                                          required =  false,
                                 defaultValue = "")
                                 String loadFrom
  ) {
    //Request is in the format
    ArrayList<String> codes = new ArrayList<>(Arrays.asList(accommodationCodes.split(";")));
    ItemRequestModel item = new ItemRequestModel(itemIds, codes, revision, section, loadFrom);
    final String token = item.generateJsonToken();
    String scrollToDivId = "";
    if (!scrollToId.equals("")) {
      try {
        scrollToDivId = "QuestionNumber_" + scrollToId.split("-")[1];
      } catch (IndexOutOfBoundsException e) {
        //Don't assign a value
      }
    }

    ModelAndView model = new ModelAndView();
    model.setViewName("item");
    model.addObject("readOnly", readOnly);
    model.addObject("token", token);
    model.addObject("scrollToDivId", scrollToDivId);
    model.addObject("item", itemIds[0]);
    return model;
  }

}