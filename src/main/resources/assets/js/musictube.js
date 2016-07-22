$(document).ready(function() {
    $('#artistNameSearch').click(search);

    function search(e) {

        var artistName = document.getElementById("artistName").value;
        console.log(artistName);

        window.location.href = "/musictube/artist/" + artistName;
    }
});

function getAlbums(artistId, artistName) {
    window.location.href = "/musictube/album/" + artistName;
}

function getTracks(artistId, artistName, albumnId, alumnName) {
    window.location.href = "/musictube/" + artistId + "/" + artistName + "/" + albumnId + "/" + alumnName +  "/track" ;
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

function uploadAlbum(artistId, albumnId) {

   // var validTracks = new Object();

    var trackRows = document.getElementById("trackTable").rows;
    var validatedTrackIds = [];

    for(var i=1 ; i<trackRows.length ; i++) {
        var trackId = trackRows[i].id;

        if($("#toggle-verify-" + trackId)[0].checked) {
            validatedTrackIds.push(trackId);

   //         var alternateUrl = null;
     //       validTracks[trackId] = alternateUrl;

        }
    }

    console.log(validatedTrackIds);
 //   console.log(validTracks);

    var json = jQuery.ajax({
        url : "/musictube/artist/" + artistId + "/album/" + albumnId,
        type : "POST",
        async : true,
        processData:false,
        data: 'json',
        dataType: 'html',
        contentType:"application/json; charset=utf-8",
        data : JSON.stringify(validatedTrackIds),
        statusCode : {
            404 : function(response) {
            }
        },
        error:function(e){

        },
        success : function(e) {
            alert("Successfully added");
        }
    });
}