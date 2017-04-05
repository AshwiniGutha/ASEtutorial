
var MongoClient = require('mongodb').MongoClient;
var assert = require('assert');
var url = 'mongodb://root:root@ds139430.mlab.com:39430/ase';
var insertDocument = function(db, callback) {
    db.collection('users').insertOne( {
        "fname" : "ashwini",
        "lname" : "gutha",
        "address":{
            "city":"Kansas City",
            "state":"MO"
        },
        "education" : {
            "university":"UMKC",
            "degree":"Master of Science",
            "major":"Computer Science"
        },
        "mail":"ashu@gmail.com"
    }, function(err, result) {
        assert.equal(err, null);
        console.log("Inserted details");
        callback();
    });
};
MongoClient.connect(url, function(err, db) {
    assert.equal(null, err);
    insertDocument(db, function() {
        db.close();
    });
});