db.system.js.save({_id: "expireAvailability", value:
  function() {
    var now = new Date();
    var today = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 0, 0, 0, 0);
    var d = today.getDate();
    var m = today.getMonth() + 1;
    var y = today.getFullYear();
    var todayString =  '' + y + '-' + (m<=9 ? '0' + m : m) + '-' + (d <= 9 ? '0' + d : d);
    var count = 0;
    db.session.find({ date: {$gte: new ISODate(todayString)}}).forEach(
      function(session) {
        sessionDateUTC = new Date(session.date.getUTCFullYear(), session.date.getUTCMonth(), session.date.getUTCDate(), 0, 0, 0, 0);
        daysLeft = parseInt(Math.round((sessionDateUTC.getTime() - today.getTime()) / (24*3600*1000)));
        diff = 0;
        session.availability.forEach(
          function(availability) {
            if(availability.type == "RM" && availability.daysBeforeSessionExpiration >= daysLeft) {
              diff += availability.total - availability.occupied;
              availability.available = 0 ;
            }
          }
        )
        session.availability.forEach(
          function(availability) {
            if(availability.type == "RM" && availability.daysBeforeSessionExpiration == (daysLeft - 1)) {
              availability.available = availability.total - availability.occupied + diff;
            }
          }
        )
        db.session.update({ _id : session._id }, session, false);
        count++;
      }
    )
    return count;
  }
});