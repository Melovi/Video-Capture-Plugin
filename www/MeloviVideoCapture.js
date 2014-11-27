function MeloviVideoCapture() {
}

MeloviVideoCapture.prototype.recordVideo = function (successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "MeloviVideoCapture", "recordVideo", []);
};

MeloviVideoCapture.prototype.stopRecordVideo = function (successCallback, errorCallback) {
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