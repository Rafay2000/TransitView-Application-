import React, { useState } from 'react';
import styles from './LoginScreen.module.css';
import { FaGoogle, FaTwitter, FaGithub } from 'react-icons/fa';

const LoginScreen = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Innlogging forsøkt:', { username, password });
  };

  return (
    <div className={styles.loginPage}>
      <div className={styles.loginContainer}>
        <h2>Logg inn på TransitView</h2>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Brukernavn eller epost"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className={styles.inputField}
          />
          <input
            type="password"
            placeholder="Passord"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className={styles.inputField}
          />

          <a href="#" className={styles.forgotPassword}>Glemt passord ?</a>

          <button type="submit" className={styles.loginButton}>
            Logg inn
          </button>
        </form>

        <hr className={styles.divider} />
        <p className={styles.socialText}>Login with social accounts</p>

        <div className={styles.socialLogins}>
          <FaGoogle className={styles.socialIcon} />
          <FaTwitter className={styles.socialIcon} />
          <FaGithub className={styles.socialIcon} />
        </div>

        <p className={styles.signUpText}>
          Ingen bruker? <a href="#" className={styles.signUpLink}>Opprett en bruker her</a>
        </p>
      </div>
    </div>
  );
};

export default LoginScreen;