function getGithubInfo(user) {
    //1. Create an instance of XMLHttpRequest class and send a GET request using it.
    // The function should finally return the object(it now contains the response!)
    let url = 'https://api.github.com/users/' + user;
    let xhttp = new XMLHttpRequest();
    xhttp.open('GET', url, false);
    xhttp.send();

    return xhttp;
}

function showUser(user) {
    //2. set the contents of the h2 and the two div elements in the div '#profile' with the user content
    // Display username, id, avatar, and profile link
    document.querySelector("#profile h2").innerHTML = 'Username: ' + user.login + '   ID: ' + user.id;
    document.getElementById('avatar').innerHTML = '<img src=' + user.avatar_url + '/>';
    document.getElementById('information').innerHTML = 'User link: ' + user.html_url;

}

function noSuchUser(username) {
    //3. set the elements such that a suitable message is displayed
    // Display error message and clear any previous avatar and info
    document.querySelector("#profile h2").innerHTML = username + ' not found.  Please try again.';
    document.getElementById('avatar').innerHTML = '';
    document.getElementById('information').innerHTML = '';
}

$(document).ready(function () {
    $(document).on('keypress', '#username', function (e) {
        //check if the enter(i.e return) key is pressed
        if (e.which == 13) {
            //get what the user enters
            username = $(this).val();
            //reset the text typed in the input
            $(this).val("");
            //get the user's information and store the response
            response = getGithubInfo(username);
            //if the response is successful show the user's details
            if (response.status == 200) {
                showUser(JSON.parse(response.responseText));
                //else display suitable message
            } else {
                noSuchUser(username);
            }
        }
    })
});
