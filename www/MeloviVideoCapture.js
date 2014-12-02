function MeloviVideoCapture() {
}

MeloviVideoCapture.prototype.recordVideo = function (successCallback, errorCallback) {
  alert("Ich komme bis in die MeloviVideoCapture.js");
  cordova.exec(successCallback, errorCallback, "MeloviVideoCapture", "recordVideo", []);
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