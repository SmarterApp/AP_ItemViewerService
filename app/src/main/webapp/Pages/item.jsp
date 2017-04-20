<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Item: ${item}</title>
        <script src="../Scripts/Libraries/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
        <script src="../Scripts/Utilities/util_xdm.js" type="text/javascript"></script>
        <script src="../Scripts/client.js" type="text/javascript"></script>
        <tds:ScriptLink source="~/Scripts/Libraries/jquery/jquery-1.10.2.min.js" type="text/javascript"></tds:ScriptLink>
        <tds:ScriptLink source="~/Scripts/Utilities/util_xdm.js" type="text/javascript"></tds:ScriptLink>
        <tds:ScriptLink source="~/Scripts/client.js" type="text/javascript"></tds:ScriptLink>
        <!-- Styling for this page only and not for IRiS interface. -->
        <tds:CSSLink href="~/IrisStyles/style.css" media="screen" type="text/css" rel="stylesheet" />
        <tds:CSSLink href="~/IrisStyles/pagenavi-css.css" media="screen" type="text/css" rel="stylesheet" />
        <tds:CSSLink href="~/IrisStyles/jd.css" media="screen" type="text/css" rel="stylesheet" />
        <tds:CSSLink href="~/IrisStyles/jd_002.css" media="screen" type="text/css" rel="stylesheet" />

        <script type="text/javascript">
            function loadItem(){
                IRiS.setFrame(frames[0]);
                // set the vendor guid.
                //Note: in the OSS IRiS case we do not care for this.
                var vendorId = '2B3C34BF-064C-462A-93EA-41E9E3EB8333';
                var token = '${token}';
                IRiS.loadToken(vendorId, token);
            };
            function irisSetup() {
                window.Util.XDM.addListener('IRiS:ready', loadItem);
            };
        </script>
        <style>
            body {
                margin: 0;
            }
            iframe {
                border: none;
                height: 100%;
                width: 100%;
            }
            .irisContainer {
                overflow: auto;
                -webkit-overflow-scrolling: touch;
                height: 100%;
                width: 100%;
            }
        </style>
    </head>
    <body>
        <div class="irisContainer">
            <iframe id="irisWindow" src="${pageContext.request.contextPath}/" onload="irisSetup()"></iframe>
        </div>
    </body>
</html>
