import React, { useState } from 'react';
import BusSelector from './components/BusSelector.jsx';
import Route from './components/Route.jsx';
import LoginScreen from './components/LoginScreen.jsx';
import './App.css'; 

function App() {
  const [currentPage, setCurrentPage] = useState('login');

  const renderContent = () => {
    if (currentPage === 'login') {
      return <LoginScreen />; 
    }
    if (currentPage === 'selector') {
      return (
        <div className="selector-page-container">
          <h2 className="selector-title">Velg Transporttype</h2>
          <BusSelector />
          </div>
      );
    }
    if (currentPage === 'schedule') {
      return <Route />;
    }
    return <h2>Side ikke funnet</h2>;
  };

  return (
    <div className="App">
      <nav className="test-nav">
        <button onClick={() => setCurrentPage('login')}>Innlogging</button>
        <button onClick={() => setCurrentPage('selector')}>Velger</button>
        <button onClick={() => setCurrentPage('schedule')}>Rute</button>
      </nav>

    <main className={`app-content ${currentPage === 'login' ? 'center-vertical' : ''}`}>
        {renderContent()}
      </main>
    </div>
  );
}

export default App;