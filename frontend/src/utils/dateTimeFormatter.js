// src/utils/dateAndTimeFormatter.js

const dateAndTimeFormatter = (timestamp, formatType = 'both') => {
    const date = new Date(timestamp);
    const day = date.getDate();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();
    const hours = date.getHours();
    const minutes = String(date.getMinutes()).padStart(2, "0");
    const ampm = hours >= 12 ? "PM" : "AM";
    const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    
    const daySuffix = (n) => {
      if (n > 3 && n < 21) return "th";
      switch (n % 10) {
        case 1: return "st";
        case 2: return "nd";
        case 3: return "rd";
        default: return "th";
      }
    };
    
    const formattedDay = `${day}${daySuffix(day)}`;
    const formattedMonth = monthNames[date.getMonth()];
    let formattedHours = hours % 12;
    formattedHours = formattedHours ? String(formattedHours).padStart(2, "0") : "12";
  
    // Switch the return format based on the formatType parameter
    if (formatType === 'time') {
      return `${formattedHours}:${minutes} ${ampm}`;
    } else if (formatType === 'date') {
      return `${formattedDay} ${formattedMonth} ${year}`;
    } else {
      return `${formattedDay} ${formattedMonth} ${year} - ${formattedHours}:${minutes} ${ampm}`;
    }
  };
  
  export default dateAndTimeFormatter;
  