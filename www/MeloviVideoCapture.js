function MeloviVideoCapture() {
}

MeloviVideoCapture.prototype.recordVideo = function (successCallback, errorCallback) {
  alert("Ich komme bis in die MeloviVideoCapture.js");
  var win = function(pluginResult) {
    var mediaFiles = [];
    var i;
    for (i = 0; i < pluginResult.length; i++) {
      mediaFiles.push(new MediaFile(
          pluginResult[i].name,
          pluginResult[i].fullPath,
          pluginResult[i].type,
          pluginResult[i].lastModifiedDate,
          pluginResult[i].size));
    }
    successCallback(mediaFiles);
  };
  cordova.exec(win, errorCallback, "MeloviVideoCapture", "recordVideo", []);
};

MeloviVideoCapture.prototype.stopRecordVideo = function (successCallback, errorCallback) {
  alert("Ich komme bis in die MeloviVideoCapture.js");
  cordova.exec(successCallback, errorCallback, "MeloviVideoCapture", "stopRecordVideo", []);
};

MeloviVideoCapture.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.melovivideocapture = new MeloviVideoCapture();
  return window.plugins.melovivideocapture;
};

cordova.addConstructor(MeloviVideoCapture.install);