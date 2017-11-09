<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="navbar.home.label"/></a></li>
        <li><g:link controller="person"><g:message code="navbar.users.label" /></g:link></li>
        <li><a class="profile" href="${createLink(uri: '/status')}"><g:message code="navbar.profile.label"/></a></li>
        <li><g:link controller="report"><g:message code="navbar.report.label" /></g:link></li>
        <li><g:link controller="login"><g:message code="navbar.login.label" /></g:link></li>
        <li><g:link controller="logout"><g:message code="navbar.logout.label" /></g:link></li>
    </ul>
</div>
