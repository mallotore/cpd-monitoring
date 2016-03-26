<!doctype html>
<html>
    <head>
        <title>Error</title>
        <meta name="layout" content="default">
    </head>
    <body>
        <section>
            <g:if env="development">
                <g:if test="${Throwable.isInstance(exception)}">
                    <g:renderException exception="${exception}" />
                </g:if>
                <g:elseif test="${request.getAttribute('javax.servlet.error.exception')}">
                        <g:renderException exception="${request.getAttribute('javax.servlet.error.exception')}" />
                </g:elseif>
                <g:else>
                    <ul class="errors">
                        <li>An error has occurred</li>
                        <li>Exception: ${exception}</li>
                        <li>Message: ${message}</li>
                        <li>Path: ${path}</li>
                    </ul>
                </g:else>
            </g:if>
            <g:else>
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12 text-center">
                            <h4 class="errors">Hubo un error inexperado, pero no te preocupes, los mejores desarrolladores están arreglándolo</h4>
                            <img class="img-responsive" src="/assets/error/error.jpg" style="margin: auto;">
                            <a href="#" onclick="window.history.go(-1); return false;" class="btn btn-default btn-lg" style="margin-top: 10px;">Volver</a>
                        </div>
                    </div>
                </div>
            </g:else>
        </section>
    </body>
</html>
