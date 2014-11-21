function MeloviVideoCapture() {
}

MeloviVideoCapture.prototype.recordAudio = function (successCallback, errorCallback, options) {
  cordova.exec(win, errorCallback, "MeloviVideoCapture", "recordAudio", []);
};

 var RecordAudio = {
    recordAudio: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'MeloviVideoCapture', // mapped to our native Java class called "Recorder"
            'recordAudio', // with this action name
            []                  //Array of arguments to pass
        ); 
    }
};

    module.exports = RecordAudio;

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