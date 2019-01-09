<script>
    window.onload = function(){
        if(gapi.auth2.getAuthInstance() == null){
            gapi.load('auth2', function() {
                gapi.auth2.init().then(function() {
                    var auth2 = gapi.auth2.getAuthInstance();
                    auth2.signOut().then(function () {
                        console.log('User signed out.');
                        window.location='/';
                    });
                });
            });
        }
    };
</script>