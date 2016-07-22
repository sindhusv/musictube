<div>
    <h1>Artist</h1>
</div>
<div class="row row-eq-height" style="flex-wrap: wrap">
<#list artists as artist>
    <div class="col-xs-6 col-md-3">
        <a id="${artist.id}" class="thumbnail" href="javascript:void(0)" onclick="getAlbums('${artist.id}', '${artist.artistName}');" style="height: 95%">
            <img src=${artist.artistThumb} alt=${artist.artistName}>
        </a>
    </div>
</#list>
</div>
