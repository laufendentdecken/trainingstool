<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="Überblick" header="">
<div class="container">
      <div class="panel panel-default" style="margin-top:30px">
        <div class="panel-heading"><strong>Aktivitäten hochladen</strong> <small>Unterstütze Formate: *.tcx</small></div>
        <div class="panel-body">

          <!-- Standar Form -->
          <h4>Einfach Datei auswählen</h4>
          <form action="/app/process" method="post" enctype="multipart/form-data" id="js-upload-form">
            <div class="form-inline">
              <div class="form-group">
                <input type="file" name="files[]" id="js-upload-files" multiple>
              </div>
              <button type="submit" class="btn btn-sm btn-primary" id="js-upload-submit">Upload files</button>
            </div>
          </form>

          <!-- Drop Zone -->
          <h4>Oder mit Drag & Drop</h4>
          <div class="upload-drop-zone" id="drop-zone">
            Just drag and drop files here
          </div>
        </div>
      </div>
    </div> <!-- /container -->  
    <script src="/js/upload.js" ></script>
</@layout.masterTemplate>