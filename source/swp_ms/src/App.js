import React from "react";
import {Link, Switch, Route, BrowserRouter} from 'react-router-dom';
import {Ms1, Ms2, Ms3} from './pages';
import MS_HEADER from './ms_header';
import MS_FOOTER from './ms_footer';

function App() {
  return (
    <div className="ms_body">
      <MS_HEADER/>
      <BrowserRouter>
      <div className='Menu-wrapper'>
        <div className="menu">
          <ul>
            <Link to='/'><li style={{border:'0px'}}>제품 관심도</li></Link>
            <Link to='/ms2'><li>광고별 연령대 시청률</li></Link>
            <Link to='/ms3'><li>광고별 연령대 평균 시청 시간</li></Link>
          </ul>
        </div>
      </div>
      <div className='Contents-wrapper'>
        <Switch>
      
          <Route exact path='/' component={Ms1} />
          <Route path='/ms2' component={Ms2} />
          <Route path='/ms3' component={Ms3} />
        </Switch>
      </div>
      </BrowserRouter>
      <MS_FOOTER/>
    </div>
  );
}

export default App;