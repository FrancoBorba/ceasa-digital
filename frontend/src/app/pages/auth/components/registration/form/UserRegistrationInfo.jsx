function UserRegistrationInfo({labelName, value}) {
  return (
    <div>
      <dt className="text-black text-sm font-stretch-expanded">{labelName}</dt>
      <dd className="text-[#7E7E7E] text-[0.95rem]">{value}</dd>
    </div>
  );
}

export default UserRegistrationInfo;
