# AP_ItemViewerService [![Build Status](https://travis-ci.org/osu-cass/AP_ItemViewerService.svg?branch=master)](https://travis-ci.org/osu-cass/AP_ItemViewerService)

# Smarter Balanced Item Viewer Service 
Renders items using Item Render as a service.

For docker versions, please see [dockerhub](https://hub.docker.com/r/osucass/ap_itemviewerservice/tags/)

Please see the docs

## Contributing

## Installation
1. Clone the repo
2. Navigate into the directory and build: `cd smarter-balanced-item-viewer && mvn clean install`

## Docker
tag iat has a different context path with no content
tag content has practice test content items

### Add context path
Default path is / and using tag iat is iatpreview

### Change memory 
JAVA_OPTS='-Xmx1g' 

### Content Volume
-v /your/content/:/home/tomcat7/content

## Reloading Items

To reload all items in item viewer service, call `Pages/API/content/reload`.

## Loading Items

The Item Viewer Service supports loading individual items or grouped performance task items.
Optional ISAAP accessibility codes may specified to enable accessibility options.

### Loading a Single Item
The URL for loading an individual item is mapped to `/item/{ItemBank-ItemID}`.
For example, to load an item with bank 123 and id 5678 the url would be `/item/123-5678`.

### Loading a Performance Task Item
To load a performance task item you need to know the banks and ids for each of the grouped items you wish to load.
The URL for loading performance task items is `/items?ids=ItemBank-ItemID1,ItemBank-ItemID2,ItemBank-ItemID3`.
For example, to load a performance task with items 187-1435, 187-1436, and 187-1437 the request would look like
`/items?ids=187-1435,187-1436,187-1437`

### Optional, Scroll to Item Id
An optional url parameter can be specified to scroll to a specific item in a performance item result. To scroll to an item, use `scrollToId` parameter and by setting to `{ItemBank-ItemID}`. Example `/items?ids=187-1435,187-1436,187-1437&scrollToId=187-1436`

### Optional, Readonly Mode
An optional url parameter can be specified to make the rendered item readonly. By default, readOnly is set to false. To enable readonly, use `readOnly` parameter and by setting to `false` or `true`. Example `/items?ids=187-1435&readOnly=true`

### Optional, Load from directory
An optional url parameter can be specified to load and or reload from a specific directory using an absolute path to the content. Example to reload items in /home/tomcat7/temp1 use `loadFrom` parameter. Example request `/items?id=187-1432&loadFrom=/home/tomact7/temp1`. This will overwrite any existing keys with the specified content in the singleton.

### Optional, Specifying Accessibility Codes
The optional accessibility codes are specified using the `isaap` url parameter.
Feature codes are passed as a semicolon separated list.
Only the feature code should be included. Feature family is not included.
For example, to load the reverse contrast and print size zoom level 1 accessibility options the parameter would look like
`issap=TDS_CCInvert;TDS_PS_L1`

Feature codes with the "&" character should be URL encoded.
It is good practice to always URL encode the list of ISAAP codes.
For a full list of feature codes please refer to the accessibility feature code
[documentation](http://www.smarterapp.org/documents/ISAAP-AccessibilityFeatureCodes.pdf).

#### Conditional and Default Acessibility `isaap` Codes
*Default Codes*
Default isaap codes should be inserted if not specified. This is to share logic as much as possible.
The following codes are default codes
```
TDS_ITM1
TDS_APC_SCRUBBER
TDS_APC_PSP
TDS_T1
TDS_F_S14TDS_FT_Verdana
TDS_ITM1
TDS_APC_SCRUBBER
TDS_APC_PSP
TDS_T1
TDS_F_S14TDS_FT_Verdana
```
*Conditional Codes*
For Text-To-Speech, if TTS is specified using any of the following codes: `TDS_TTS_Stim, TDS_TTS_Item, or TDS_TTS_Stim&TDS_TTS_Item`.
Then insert the following options:
```
TDS_TTSAA_Volume - TTS Audio Adjustments
TDS_TTSAA_Pitch - TTS Audio Adjustments
TDS_TTSAA_Rate -TTS Audio Adjustments
TDS_TTSAA_SelectVP - TTS Audio Adjustments
TDS_TTX_A203 - TTX Business Rules
TDS_TTSPause1 - TTS Pausing
```
### Examples
Loading item 187-856 with the yellow on blue color contrast and print zoom level 4 accessibility options.  
`http://itemviewerservice.example/item/187-856?isaap=TDS_CCYellowB;TDS_PS_L4`


Loading performance task with items 187-1435, 187-1436, and 187-1437, 
and the yellow on blue color contrast and print zoom level 4 accessibility options.  
`http://itemviewerservice.example/items?ids=187-1435,187-1436,187-1437&isaap=TDS_CCYellowB;TDS_PS_L4`


## Getting Involved
We would be happy to receive feedback on its capabilities, problems, or future enhancements:
* For general questions or discussions, please use the [Forum](http://forum.opentestsystem.org/viewforum.php?f=9)
* Use the Issues link to file bugs or enhancement requests.
* Feel free to Fork this project and develop your changes!

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Test locally! `mvn clean install` && `mvn checkstyle:check`
5. Push to the branch: `git push origin my-new-feature`
6. Submit a pull request :smile:

## License
This project is licensed under the [Mozilla Public License Version 2.0](https://www.mozilla.org/en-US/MPL/2.0/).
