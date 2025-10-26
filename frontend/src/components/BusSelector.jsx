import React, { useState } from 'react';
import styles from './BusSelector.module.css';
import { FaBus, FaPlane, FaTaxi } from 'react-icons/fa';

const BusOption = ({ type, icon, isSelected, onClick }) => (
  <div
    className={`${styles.busOption} ${isSelected ? styles.selected : ''}`}
    onClick={() => onClick(type)}
  >
    <div className={styles.iconContainer}>
      {icon}
    </div>
    <span className={styles.typeLabel}>{type}</span>
  </div>
);
// BusSelector komponenten
const BusSelector = () => {
  const [selectedBus, setSelectedBus] = useState('Buss');

  const busTypes = [
    { type: 'Buss', icon: <FaBus size={32} /> },
    { type: 'Superduperbuss', icon: <FaPlane size={32} /> },
    { type: 'Taxi', icon: <FaTaxi size={32} /> },
  ];

  const handleSelect = (type) => {
    setSelectedBus(type);
    console.log(`Valgt buss: ${type}`);
  };

  return (
    <div className={styles.selectorContainer}>
      {busTypes.map((bus) => (
        <BusOption
          key={bus.type}
          type={bus.type}
          icon={bus.icon}
          isSelected={selectedBus === bus.type}
          onClick={handleSelect}
        />
      ))}
    </div>
  );
};

export default BusSelector;