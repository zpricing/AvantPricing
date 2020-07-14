db.system.js.save({_id: "statsAdditionalData", value:
  function() {
    var count = 0;
    db.statistic.find({sessionId: {$exists: true}, sessionTime: {$exists: false}, date: {$gt: ISODate("2013-08-01")}}).forEach(
      function(stat) {
        session = db.session.findOne({cinemaId: stat.cinemaId, sessionId: stat.sessionId});
        if (session != null) {
          db.statistic.update({_id: stat._id}, {$set: {sessionTime: session.time, originalMovieName: session.movieName}});
          count++;
        }
      }
    )
    
    db.statistic.find({type: "second_selling", selectedSessionId: {$exists: true}, selectedTicketTypeId: {$exists: true}, selectedPrice: {$exists: false}, date: {$gt: ISODate("2013-08-01")}}).forEach(
      function(stat) {
        session = db.session.findOne({cinemaId: stat.cinemaId, sessionId: stat.selectedSessionId});
        if (session != null) {
          count++;
          session.availability.forEach(
            function (availability) {
              if (availability.ticketTypeId == stat.selectedTicketTypeId) {
                var priceWithBookingFee = parseFloat(availability.ticketPrice, 10) + parseFloat(availability.ticketBookingFee, 10);
                db.statistic.update({_id: stat._id}, {$set: {selectedSessionTime: session.time, selectedPrice: priceWithBookingFee.toFixed(4)}});
              }
            }
          )
        }
      }
    )
    
    return count;
  }
});