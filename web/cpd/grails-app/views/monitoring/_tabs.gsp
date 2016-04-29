 <div class="collapse navbar-collapse navbar-right navbar-main-collapse" style="margin-top: 15px; margin-bottom:15px;">
    <ul class="nav nav-tabs nav-justified">
      <li class="subtab ${selected == 'overview' ? 'selected-subtab' : ''}"><a href="${g.createLink(mapping:'overviewstats', absolute:'true')}">Overview</a></li>
      <li class="subtab ${selected != 'overview' ? 'selected-subtab' : ''}"><a href="${g.createLink(mapping:'historicalstats', absolute:'true')}">Histórico</a></li>
    </ul>
</div>