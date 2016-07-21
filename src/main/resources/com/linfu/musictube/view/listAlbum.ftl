<div>
    <h1>${albums[0].artistName}</h1>
</div>
<div>
    <#list albums as album>
        <div class="col-xs-6 col-md-3">
            <a id="${album.id}" class="thumbnail" href="javascript:void(0)" onclick="getTracks('${album.artistId}', '${album.artistName}', '${album.id}', '${album.title}');">
                <img src=${album.albumArt} alt=${album.title}>
            </a>
        </div>
    </#list>
</div>
