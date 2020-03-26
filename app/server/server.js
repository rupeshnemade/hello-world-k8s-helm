const mongoose = require("mongoose");
const express = require("express");
const cors = require("cors");
const {
  MONGO_URL,
  MONGO_DATABASE,
  MONGO_USERNAME,
  MONGO_PASSWORD
}  = process.env;
const app = express();
app.use(cors());
app.use(express.json());

const PoiSchema = mongoose.Schema({
  msg: String
});
const Poi =  mongoose.model("Poi", PoiSchema);

app.get('/', async (req, res) => {
  await Poi.findOne().lean().exec(function(err, grp) {
    console.log(grp.msg) ;
    return res.json(grp.msg);
   });
});

mongoose.connect(
  `mongodb://${MONGO_URL}/${MONGO_DATABASE}`,
  {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    auth: {
     user: MONGO_USERNAME,
     password: MONGO_PASSWORD
    }
   }
  ).then(
    async () => {
    app.listen(3000, () => { 
		new Poi({msg : "Hello World"}).save(); 
		console.log('Server listening...')});
	}
  );

const db = mongoose.connection;
db.on("error", console.error.bind(console, "connection error:"));
db.once("open", function() {
  console.log("Connected to Mongo!");
});

