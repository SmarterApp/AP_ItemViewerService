<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Item: ${item}</title>
        <script src="${pageContext.request.contextPath}/Scripts/Libraries/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/Scripts/Utilities/util_xdm.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/Scripts/iris.client.js" type="text/javascript"></script>
        <!-- Styling for this page only and not for IRiS interface. -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/IrisStyles/iris.css">

        <script type="text/javascript">
            function loadItem(){
                IRiS.setFrame(frames[0]);
                // set the vendor guid.
                //Note: in the OSS IRiS case we do not care for this.
                var vendorId = '2B3C34BF-064C-462A-93EA-41E9E3EB8333';
                var token = '${token}';
                var scrollToDivId = '${scrollToDivId}';
                var readOnly = ${readOnly};
                IRiS.loadToken(vendorId, token, readOnly, scrollToDivId);
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
            <iframe id="irisWindow" src="${pageContext.request.contextPath}/IrisPages/itemRender.xhtml" onload="loadItem()"></iframe>
        </div>
    </body>
</html>
