$(document).ready(function() {
    $('#artistNameSearch').click(search);

    function search(e) {

        var artistName = document.getElementById("artistName").value;
        console.log(artistName);

        window.location.href = "/musictube/album/" + artistName;
    }
});

function getTracks(albumnId, alumnName, artistName) {
    window.location.href = "/musictube/track/" + albumnId + "/" + alumnName + "/" + artistName;
}

function refreshYoutubeLink(trackId) {
    var alternateLink = document.getElementById("input-youtubeAlternateLink-" + trackId).value;
    var videoId = alternateLink.match(/v=(.*)&/g);
    var videoIdLength = 0;
    if(videoId == null) {
        videoId = alternateLink.match(/v=(.*)/g)[0];
        videoIdLength = videoId.length;
        videoId = videoId.substring(2,videoIdLength);
    }else {
        videoId = videoId[0];
        videoIdLength = videoId.length;
        videoId = videoId.substring(2,videoIdLength-1);
    }

    console.log(videoId);

    document.getElementById("iframe-youtubeLink-" + trackId).src = "https://www.youtube.com/embed/" + videoId;
}

function uploadAlbum(albumnId) {

    var trackRows = document.getElementById("trackTable").rows;
    var validatedTrackIds = [];

    for(var i=1 ; i<trackRows.length ; i++) {
        var trackId = trackRows[i].id;

        if($("#toggle-verify-" + trackId)[0].checked) {
            validatedTrackIds.push(trackId);
        }
    }

    console.log(validatedTrackIds);

    var json = jQuery.ajax({
        url : "/musictube/album/" + albumnId,
        type : "POST",
        async : true,
        processData:false,
        data: 'json',
        dataType: 'html',
        contentType:"application/json; charset=utf-8",
        statusCode : {
            404 : function(response) {
            }
        },
        error:function(e){

        },
        data : JSON.stringify(validatedTrackIds),
        success : function(e) {

        }
    });
}