<!doctype html>
<html>
    <head>
        <meta name="layout" content="default">
        <title>Página no encontrada</title>
    </head>
    <body>
        <section>
            <div class="container">
                <div class="row">
                    <div class="col-lg-12 text-center">
                        <h3 class="errors">¡La página no existe!</h3>
                        <div class="error-container">
                            <span>${request.forwardURI}</span>
                        </div>
                        <a href="#" onclick="window.history.go(-1); return false;" class="btn btn-default btn-lg" style="margin-top: 10px;">Volver</a>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
