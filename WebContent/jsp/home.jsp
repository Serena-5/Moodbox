<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>MoodBox  Home</title>
  <style>
    :root {
      --primary:#0078d7;
      --accent:#e91e63;
      --bg:#ffffff;
      --text:#333;
    }
    *{box-sizing:border-box;margin:0;padding:0;font-family:"Segoe UI", Tahoma, Arial, sans-serif;}

    body{min-height:100vh;width:100%;background:var(--bg);}

    .hero{
      position:relative;
      width:100%;
      height:100vh;
      display:grid;
      grid-template-columns:1fr 1fr;
      overflow:hidden;
    }

    .hero-right{
      background:url('${pageContext.request.contextPath}/images/boxes-banner.png') center/cover no-repeat;
    }

    .hero-right::before{
      content:"";
      position:absolute;
      top:0;right:50%;
      width:100%;height:100%;
      background:inherit;
      border-top-left-radius:55% 100%;
      border-bottom-left-radius:55% 100%;
      transform:translateX(50%);
    }

    .hero-left{
      position:relative;
      z-index:3;
      display:flex;
      flex-direction:column;
      justify-content:center;
      align-items:flex-start;
      text-align:left;
      padding:80px 60px;
      gap:28px;
      background:rgba(255,255,255,0.9);
    }

    .brand img{
      height:300px;
      width:auto;
      object-fit:contain;
      margin-bottom:0;
    }

    h1{font-size:3rem;color:var(--accent);} 
    p{font-size:1.05rem;color:var(--text);line-height:1.6;max-width:420px;}

    .cta{
      display:inline-block;
      padding:16px 36px;
      background:var(--primary);
      color:#fff;
      border-radius:40px;
      font-weight:600;
      text-decoration:none;
      transition:.25s;
    }
    .cta:hover{background:#005fb3;}

    .nav{
      position:absolute;
      top:0;left:0;width:100%;
      padding:20px 60px;
      display:flex;
      justify-content:space-between;
      align-items:center;
      z-index:3;
    }
    .nav ul{list-style:none;display:flex;gap:32px;}
    .nav a{color:#fff;text-decoration:none;font-weight:600;font-size:1rem;transition:.25s;}
    .nav a:hover{opacity:.8;}

    .nav-logo img{
      height:60px;
      width:auto;
      object-fit:contain;
      cursor:pointer;
    }

    .nav::after{
      content:"";position:absolute;top:0;left:0;width:100%;height:100%;background:linear-gradient(90deg,rgba(0,0,0,.35),rgba(0,0,0,.15) 40%,transparent);z-index:-1;
    }

    @media(max-width:900px){
      .hero{grid-template-columns:1fr;}
      .hero-right{height:50vh;}
      .hero-right::before{display:none;}
      .hero-left{padding:50px 30px;text-align:center;align-items:center;}
      .nav ul{gap:18px;}
    }
  </style>
</head>
<body>
  <header class="nav">
    <a href="${pageContext.request.contextPath}/home" class="nav-logo">
      <img src="${pageContext.request.contextPath}/images/logo.png" alt="MoodBox"/>
    </a>
    <ul>
      <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/box">Catalogo</a></li>
      <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
    </ul>
  </header>

  <section class="hero">
    <div class="hero-left">
      <div class="brand">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="MoodBox" />
      </div>
      <h1>Benvenuto in MoodBox</h1>
      <p>Scopri la magia delle nostre box a tema: ogni scatola e' un viaggio sensoriale che trasforma il tuo umore in un'esperienza unica da vivere e condividere.</p>
      <a href="${pageContext.request.contextPath}/box" class="cta">Scopri le box</a>
    </div>
    <div class="hero-right"></div>
  </section>
</body>
</html>
