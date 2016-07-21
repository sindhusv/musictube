<div style="display: flex">
    <h2 style="width: 86.2%">
        ${artistName} - ${albumName}
    </h2>
    <button id="btn-upload-${albumId}" class="btn btn-info" type="button" style="background-color: grey; margin: 0px; width: 12%; align-self: center" onclick="uploadAlbum('${artistId}', '${albumId}')">Upload</button>
</div>



<div>
    <table id="trackTable" class="table">
        <tr>
            <th>Track No</th>
            <th>Title</th>
            <th>Youtube Link</th>
            <th>Uploader</th>
            <th>Verify</th>
        </tr>
        <#list tracks as track>
            <tr id="${track.id}">
                <td>${track.trackNumber}</td>
                <td>${track.title}</td>
                <td>
                    <div style="width: 420px; margin-bottom: 10px; background-color: whitesmoke; color: grey; display: flex; justify-content: center">
                        <input type="text" id="input-youtubeAlternateLink-${track.id}" placeholder="Alternate link" style="width: 332px; height: 42px">
                        <button id="btn-youtubeAlternateLink-${track.id}" class="btn btn-success" type="button" style="background-color: grey; margin: 0px" onclick="refreshYoutubeLink('${track.id}')">Go!</button>
                    </div>
                    <iframe id="iframe-youtubeLink-${track.id}" width="420" height="315"
                            src="https://www.youtube.com/embed/${track.youtubeLink}">
                    </iframe>
                </td>
                <td>${track.youtubeChannelTitle}</td>
                <td>
                    <input id="toggle-verify-${track.id}" type="checkbox" data-toggle="toggle" data-on="Verfied" data-off="Not Verified" style="text-align: left">
                </td>
            </tr>
        </#list>
    </table>

</div>
