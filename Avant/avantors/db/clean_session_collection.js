db.system.js.save({_id: "cleanSessionCollection", value:
  function() {
    var now = new Date();
    var today = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 0, 0, 0, 0);
    db.session.remove({date: {$lt: today}})
  }
});