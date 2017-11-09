<html>
<head>
    <meta name="layout" content="main"/>
    <title>What Are You Doing?</title>
</head>
<body>
    <g:render template="/navbar" />
        <div class="column mainboard">
            <h1>Welcome To The Twitter Grails App</h1>
            <h2>Latest Tweets:</h2>
            <div id="messages">
                <twitter:renderMessages messages="${statusMessages}"/>
            </div>
        </div>

    <asset:javascript src="spring-websocket" />
    <script type="text/javascript">
        $(function() {
            var socket = new SockJS("${createLink(uri: "/stomp")}");
            var client = Stomp.over(socket);

            function sendStatusUpdate(message) {
                if (message) {
                    client.send("/app/updateStatus", {}, message);
                }
            }

            function receiveStatusUpdate(message) {
                $(message.body).prependTo("#messages").hide().slideDown();
            }

            $("#updateStatusForm").submit(function() {
                var $message = $("#message");
                sendStatusUpdate($.trim($message.val()));
                $message.val("");
                return false;
            });

            client.connect({}, function() {
                client.subscribe("/user/queue/timeline", receiveStatusUpdate);
            });
        });
    </script>
</body>
</html>
