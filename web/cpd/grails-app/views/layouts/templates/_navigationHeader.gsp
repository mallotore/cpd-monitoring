<nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" style="background-color: white;" data-toggle="collapse" data-target=".navbar-main-collapse">
                <i class="fa fa-bars"></i>
            </button>
            <a class="navbar-brand page-scroll home" href="${g.createLink(mapping:'home', absolute:'true')}">
            </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-right navbar-main-collapse">
            <ul class="nav navbar-nav">
                <!-- Hidden li included to remove active class from about link when scrolled up past about section -->
                <li class="hidden">
                    <a href="#page-top"></a>
                </li>
                <li class="${pageProperty(name:'page.defConfig') == '1' ? 'selected' : ''}">
                    <a class="page-scroll" href="${g.createLink(mapping:'configuration', absolute:'true')}">Configuración</a>
                </li>
                <li class="${pageProperty(name:'page.defMonitoringStats') == '1' ? 'selected' : ''}">
                    <a class="page-scroll" href="${g.createLink(mapping:'overviewstats', absolute:'true')}">Estadísticas</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
</nav>