import os
from geopy.geocoders import Nominatim
from flask import Flask
from flask import request

app = Flask(__name__)
geolocator = Nominatim()

@app.route("/")
def index():
    return "Python Flask SparkPi server running. Add the 'sparkpi' route to this URL to invoke the app."


@app.route("/state",methods=['GET'])
def geolocationapi():
    lat = str(request.args.get('lat', ''))
    lon = str(request.args.get('lon', ''))
    latlon = lat+","+lon
    location = geolocator.reverse(latlon)
    return location.raw['address']['state']

if __name__ == "__main__":
    port = int(os.environ.get("PORT", 8081))
    app.run(host='0.0.0.0', port=port)