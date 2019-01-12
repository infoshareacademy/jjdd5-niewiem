
<h2>Sign in with Google for more functionality:</h2>
<br>
<div class="g-signin2" data-onsuccess="onSignIn"></div>


<script>
    function onSignIn(googleUser) {
        var profile = googleUser.getBasicProfile();
        console.log('ID: ' + profile.getId());
        console.log('Name: ' + profile.getName());
        console.log('Image URL: ' + profile.getImageUrl());
        console.log('Email: ' + profile.getEmail());
        console.log('id_token: ' + googleUser.getAuthResponse().id_token);

        var redirectUrl = 'id-token';

        var form = $('<form action="' + redirectUrl + '" method="post">' +
                '<input type="text" name="id_token" value="' +
                googleUser.getAuthResponse().id_token + '" />' +
                '</form>');
        $('body').append(form);
        if (googleUser.getAuthResponse().id_token != null && profile.getId() != null) {
            form.submit();
        }
    }
</script>