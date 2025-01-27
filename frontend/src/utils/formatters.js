import moment from 'moment';

export const formatDate = (dateStr, organizacao) => {

  if (organizacao?.timeZoneOffset !== undefined) {
    return moment(dateStr)
      .utcOffset(organizacao.timeZoneOffset)
      .format("DD/MM/YYYY");
  }

  return moment(dateStr).format("DD/MM/YYYY");
}
export const formatTime = (timeStr) => {
  return timeStr.slice(0, 5)
}

export const formatPhoneNumber = (phone) => {
  return `(${phone.slice(0, 2)}) 9 ${phone.slice(2, 7)}-${phone.slice(7)}`
}

export default { formatDate, formatTime, formatPhoneNumber }
