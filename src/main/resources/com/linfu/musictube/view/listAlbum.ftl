<div>
    <h1>Albums - ${albums[0].artistName}</h1>
</div>
<div class="row row-eq-height" style="flex-wrap: wrap">
    <#list albums as album>
        <div class="col-xs-6 col-md-3">
            <a id="${album.id}" class="thumbnail" href="javascript:void(0)" onclick="getTracks('${album.artistId}', '${album.artistName}', '${album.id}', '${album.title}');" style="height: 95%">
                <img src=${album.albumArt} alt=${album.title}>
            </a>
        </div>
    </#list>
</div>
