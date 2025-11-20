import React, { useState } from "react";
import { MapContainer, TileLayer, Marker, useMapEvents } from "react-leaflet";
import L from "leaflet";

// Fix default marker icon issue in Leaflet + Webpack
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
    iconUrl: require("leaflet/dist/images/marker-icon.png"),
    shadowUrl: require("leaflet/dist/images/marker-shadow.png"),
});

function LocationMarker({ onSelect }) {
    const [position, setPosition] = useState(null);

    useMapEvents({
        click(e) {
            setPosition(e.latlng);
            onSelect(e.latlng);
        },
    });

    return position === null ? null : <Marker position={position} />;
}

export default function MapPicker() {

    const [selected, setSelected] = useState({ lat: 12.9716, lng: 77.5946 });

    return (
        <div>
            <MapContainer
                center={[selected.lat, selected.lng]}
                zoom={13}
                style={{ height: "400px", width: "100%" }}
            >
                <TileLayer
                    attribution='&copy; <a href="https://osm.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                <LocationMarker onSelect={(coords) => setSelected(coords)} />
            </MapContainer>

            <p>Latitude: {selected.lat}</p>
            <p>Longitude: {selected.lng}</p>
        </div>
    );
}
