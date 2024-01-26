import { useState } from 'react';
import './App.css';
import Header from './Header/Header';
import Principal from './Main/Principal';
import Footer from './Footer/Footer';
import FormCrearProducto from './FormCrear/FormCrearProducto';

function App() {
  const [modalVisible, setModalVisible] = useState(false);

  const toggleModal = () => {
    setModalVisible(!modalVisible);
    console.log('Modal visible:', modalVisible);
  };

  return (
    <div>
      <Header onAgregarClick={toggleModal} />
      <Principal />
      {modalVisible && <FormCrearProducto onClose={toggleModal} />}
      <Footer />
    </div>
  );
}

export default App;