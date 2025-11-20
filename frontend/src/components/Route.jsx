import React from 'react';
import styles from './Route.module.css';

const RouteStop = ({ stopName, times, details }) => (
  <div className={styles.routeStop}>
    <h3>{stopName}</h3>
    <div className={styles.timeInfo}>
      {times.split(' | ').map((time, index) => (
        <span key={index} className={styles.timeEntry}>{time}</span>
      ))}
    </div>
    <p className={styles.details}>
      Estimert kjøretur {details.time} min / {details.distance} avstand /
      "{details.note}"
    </p>
  </div>
);

const Route = ({ routeData }) => {
  return (
    <div className={styles.scheduleContainer}>
      <div className={styles.header}>
        <h1>Rute "{routeData.routeId}" ({routeData.from} til {routeData.to})</h1>
        <h2>/ Dato valgt: {routeData.date}</h2>
      </div>

      
      <div className={styles.busDetails}>
        <p>Buss {routeData.busNumber} | Bus type: {routeData.busType} | {routeData.capacity}</p>
      </div>

      {routeData.stops.map((stop, index) => (
        <React.Fragment key={index}>
          <RouteStop
            stopName={stop.name}
            times={stop.times}
            details={stop.details}
          />
          {index < routeData.stops.length - 1 && <hr className={styles.separator} />}
        </React.Fragment>
      ))}
    </div>
  );
};

const exampleRouteData = {
  routeId: "OO-RS12",
  from: "Babylon",
  to: "Oslo",
  date: "2056-01-01",
  busNumber: "B11",
  busType: "Buss - Elektrisk",
  capacity: "40 seter",
  stops: [
    {
      name: "Trondheim Busstasjon",
      times: "08:50 | 09:00",
      details: { time: 8, distance: "5.0 KM", note: "Stoppes ved Rema 1000" }
    },
    {
      name: "Røros Videregående",
      times: "09:09 | 09:25 | Åsfjell",
      details: { time: 15, distance: "12.0 KM", note: "Stoppes ved store parkeringsplassen" }
    },
  ]
};

export default () => <Route routeData={exampleRouteData} />;