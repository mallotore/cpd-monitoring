<!doctype html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Welcome to Grails</title>
        <style type="text/css" media="screen">
            #status {
                background-color: #eee;
                border: .2em solid #fff;
                margin: 2em 2em 1em;
                padding: 1em;
                width: 12em;
                float: left;
                -moz-box-shadow: 0px 0px 1.25em #ccc;
                -webkit-box-shadow: 0px 0px 1.25em #ccc;
                box-shadow: 0px 0px 1.25em #ccc;
                -moz-border-radius: 0.6em;
                -webkit-border-radius: 0.6em;
                border-radius: 0.6em;
            }

            #status ul {
                font-size: 0.9em;
                list-style-type: none;
                margin-bottom: 0.6em;
                padding: 0;
            }

            #status li {
                line-height: 1.3;
            }

            #status h1 {
                text-transform: uppercase;
                font-size: 1.1em;
                margin: 0 0 0.3em;
            }

            #page-body {
                margin: 2em 1em 1.25em 18em;
            }

            h2 {
                margin-top: 1em;
                margin-bottom: 0.3em;
                font-size: 1em;
            }

            p {
                line-height: 1.5;
                margin: 0.25em 0;
            }

            #controller-list ul {
                list-style-position: inside;
            }

            #controller-list li {
                line-height: 1.3;
                list-style-position: inside;
                margin: 0.25em 0;
            }

            @media screen and (max-width: 480px) {
                #status {
                    display: none;
                }

                #page-body {
                    margin: 0 1em 1em;
                }

                #page-body h1 {
                    margin-top: 0;
                }
            }
        </style>
    </head>
    <body>
        <a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div id="status" role="complementary">
            <h1>Content</h1>
        </div>
        <div id="page-body" role="main">
            <h1>Elements from server</h1>
            <div>
                <h2>osInformation</h2>
                <p>dataModel   ---- ${osInformation.DataModel}</p>
                <p>cpuEndian   ---- ${osInformation.CpuEndian}</p>
                <p>name   ---- ${osInformation.Name}</p>
                <p>version   ---- ${osInformation.Version}</p>
                <p>arch   ---- ${osInformation.Arch}</p>
                <p>description   ---- ${osInformation.Description}</p>
                <p>patchLevel   ---- ${osInformation.PatchLevel}</p>
                <p>vendor   ---- ${osInformation.Vendor}</p>
                <p>vendorName   ---- ${osInformation.VendorName}</p>
                <p>vendorVersion   ---- ${osInformation.VendorVersion}</p>
            </div>
            <div>
             <g:each in="${diskInformation}" var="it">
                <div>
                    <h2>disk space</h2>
                    <p>path   ---- ${it.path}</p>
                    <p>totalSpace   ---- ${it.totalSpace}</p>
                    <p>freeSpace   ---- ${it.freeSpace}</p>
                    <p>usableSpace   ---- ${it.usableSpace}</p>
                </div>
            </g:each>
            <g:each in="${winServicesStatus}" var="it">
                <div>
                    <h2>winServicesStatus</h2>
                    <p>name   ---- ${it.name}</p>
                    <p>status   ---- ${it.status}</p>
                </div>
            </g:each>
            <div>
                <h2>ProcessInformation</h2>
                <p>apache2Id   ---- ${apache2Id}</p>
                <p>mysqlId   ---- ${mysqlId}</p>
                <p>iisId   ---- ${iisId}</p>
                <p>tomcatId   ---- ${tomcatId}</p>
            </div>
        </div>
    </body>
</html>