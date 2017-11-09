<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <style type='text/css' media='screen'>
        #login {
            margin:15px 0px; padding:0px;
            text-align:center;
        }
        #login .inner {
            width:550px;
            margin:0px auto;
            text-align:left;
            padding:10px;
            border-top:1px dashed #499ede;
            border-bottom:1px dashed #499ede;
            background-color:#EEF;
        }
        #login .inner .fheader {
            padding:4px;margin:3px 0px 3px 0;color:#2e3741;font-size:14px;font-weight:bold;
        }
        #login .inner .cssform p {
            clear: left;
            margin: 0;
            padding: 5px 0 8px 0;
            padding-left: 105px;
            border-top: 1px dashed gray;
            margin-bottom: 10px;
            height: 1%;
        }
        #login .inner .cssform input[type='text'] {
            width: 120px;
        }
        #login .inner .cssform label {
            font-weight: bold;
            float: left;
            margin-left: -105px;
            width: 100px;
        }
        #login .inner .login_message {color:red;}
        #login .inner .text_ {width:120px;}
        #login .inner .chk {height:12px;}
        </style>
    </head>
    <body>

    <g:render template="/navbar" />

    <div id='login'>
        <div class='inner'>
            <a href="#create-person" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

            <div id="create-person" class="content scaffold-create" role="main">
                <h1>New to Twitter? Sign up</h1>
                <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${this.person}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${this.person}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
                </g:hasErrors>
                <g:form action="save" class='cssform'>
                    <fieldset class="form">
                        %{--<f:all bean="person" />--}%
                        <p>
                            <label for='username'>Login ID</label>
                            <span class="required-indicator">*</span>
                            <input type='text' class='text_' name='userName' id='username' required />
                        </p>
                        <p>
                            <label for='email'>E-mail</label>
                            <span class="required-indicator">*</span>
                            <input type='text' class='text_' name='email' id='email' required />
                        </p>
                        <p>
                            <label for='first_name'>First Name</label>
                            <span class="required-indicator">*</span>
                            <input type='text' class='text_' name='firstName' id='first_name' required />
                        </p>
                        <p>
                            <label for='last_name'>Last Name</label>
                            <span class="required-indicator">*</span>
                            <input type='text' class='text_' name='lastName' id='last_name' required />
                        </p>
                        <p>
                            <label for='password'>Password</label>
                            <span class="required-indicator">*</span>
                            <input type='password' class='text_' name='password' id='password' required />
                        </p>
                    </fieldset>
                    <fieldset class="buttons">
                        <g:submitButton name="create" class="save" value="Sing up" />
                    </fieldset>
                </g:form>
            </div>
        </div>
    </div>
    </body>
</html>
