function MeloviVideoCapture() {
}

MeloviVideoCapture.prototype.recordAudio = function (successCallback, errorCallback, options) {

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

  cordova.exec(win, errorCallback, "MeloviVideoCapture", "recordAudio", []);
};

var MediaFile = function(name, fullPath, type, lastModifiedDate, size) {
  this.name = name;
  this.fullPath = fullPath;
  this.type = type;
  this.lastModifiedDate = lastModifiedDate;
  this.size = size;
};

MediaFile.prototype.getFormatData = function(successCallback, errorCallback) {
  if (typeof this.fullPath === "undefined" || this.fullPath === null) {
    errorCallback("invalid argument");
  } else {
    cordova.exec(successCallback, errorCallback, "MeloviVideoCapture", "getFormatData", [this.fullPath, this.type]);
  }
};

MeloviVideoCapture.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.melovivideocapture = new MeloviVideoCapture();
  return window.plugins.melovivideocapture;
};

cordova.addConstructor(MeloviVideoCapture.install);