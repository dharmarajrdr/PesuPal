import { useState } from 'react';
import './RecordFormLayout.css';

const fieldIcons = {
    "STRING": "fa fa-edit",
    "USER": "fa fa-user",
    "DATE_TIME": "fa fa-calendar",
    "TEXT": "fa fa-paragraph",
    "SELECT": "fa fa-list",
    "TRANSITION": "fa fa-chart-line",
    "CURRENCY": "fa fa-dollar-sign",
    "GEO_LOCATION": "fa fa-map-marker-alt",
    "FILE": "fa fa-file",
    "LINK": "fa fa-link"
}

const FieldName = ({ fieldName, fieldType, required }) => {

    return <div className='field-name'>
        <i className={`mR10 w15 color777 ${fieldIcons[fieldType] || 'fa fa-question'}`} aria-hidden='true'></i>
        <span className='field-name-text'>{fieldName}</span>
        {required && <span className='required'>*</span>}
    </div>;
}

const FieldValue = ({ field, onChange }) => {

    const { fieldId, fieldType, data, editable } = field || {};
    const isReadOnly = !editable;

    switch (fieldType) {
        case 'STRING': {
            return (
                <input
                    type="text"
                    className="field-value"
                    value={data || ""}
                    name={`fieldValue_${fieldId}`}
                    placeholder="Enter text"
                    readOnly={isReadOnly}
                    onChange={e => editable && onChange(fieldId, e.target.value)}
                />
            );
        }

        case 'USER': {
            return <p className="field-value">User select box will come here...</p>;
        }

        case 'DATE_TIME': {
            const date = new Date(data);
            const pad = num => num.toString().padStart(2, '0');
            const formatted = data
                ? `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T${pad(date.getHours())}:${pad(date.getMinutes())}`
                : "";
            return (
                <input
                    type="datetime-local"
                    className="field-value"
                    value={formatted}
                    readOnly={isReadOnly}
                    onChange={e => editable && onChange(fieldId, e.target.value)}
                />
            );
        }

        case 'TEXT': {
            return (
                <textarea
                    className="field-value"
                    placeholder={field.fieldName}
                    value={data || ""}
                    readOnly={isReadOnly}
                    onChange={e => editable && onChange(fieldId, e.target.value)}
                />
            );
        }

        case 'SELECT': {
            const selectedValue = data?.find(opt => opt.selected)?.id || "";
            return (
                <select
                    className="field-value"
                    value={selectedValue}
                    disabled={isReadOnly}
                    onChange={e => editable && onChange(fieldId, e.target.value)}
                >
                    <option value="" disabled>Select an option</option>
                    {data?.map(({ id, value, selected }, index) => (
                        <option key={index} value={id} selected={selected}>
                            {value}
                        </option>
                    ))}
                </select>
            );
        }

        case 'TRANSITION': {
            const { name, score } = data || {};
            return name && (
                <p className="field-value transition-value" title={`Score: ${score}`}>
                    {name}
                </p>
            );
        }

        case 'CURRENCY': {
            const selectedCurrency = data?.currency?.find(c => c.selected)?.code || "";
            return (
                <div className="field-value currency-field FRCS">
                    <select
                        className="field-value"
                        value={selectedCurrency}
                        disabled={isReadOnly}
                        onChange={e => editable && onChange(fieldId, e.target.value)}
                    >
                        <option value="" disabled>
                            Select a Currency
                        </option>
                        {data?.currency?.map(({ code, name }, index) => (
                            <option key={index} value={code} title={name}>
                                {code}
                            </option>
                        ))}
                    </select>
                    <input
                        type="number"
                        className="field-value"
                        value={data?.amount || ""}
                        placeholder="Enter Amount"
                        readOnly={isReadOnly}
                        onChange={e => editable && onChange(fieldId, e.target.value)}
                    />
                </div>
            );
        }

        case 'GEO_LOCATION': {
            return <p className="field-value">Geo location select box will come here...</p>;
        }

        case 'FILE': {
            return (
                <input
                    type="file"
                    className="field-value"
                    disabled={isReadOnly}
                />
            );
        }

        case 'LINK': {
            return (
                <input
                    type="url"
                    className="field-value"
                    placeholder="Enter URL"
                    value={data || ""}
                    readOnly={isReadOnly}
                    onChange={editable ? () => { } : undefined}
                />
            );
        }
    }
};

const getDataFromField = (field) => {
    const { fieldType, data } = field || {};
    switch (fieldType) {
        case 'STRING': {
            return data || "";
        }
        case 'NUMBER': {
            return data || "";
        }
        case 'DATE_TIME': {
            return data ? new Date(data).toISOString() : "";
        }
        case 'BOOLEAN': {
            return data ? "true" : "false";
        }
        case 'SELECT': {
            return data?.find(opt => opt.selected)?.id || "";
        }
        case 'TRANSITION': {
            return data?.name || "";
        }
        case 'CURRENCY': {
            const amount = data?.amount || "";
            const currency = data?.currency?.find(c => c.selected)?.code || "";
            return { amount, currency };
        }
    }
}

const RecordField = ({ field, componentType, onChange }) => {

    const { showInDetail, fieldType, required, editable, classification } = field || {};
    if (classification == 'SYSTEM_FIELD' && componentType == 'create') {
        return null; // Skip system fields only on creating record
    }
    return showInDetail && (
        <div className={`FRSS w100 record-field ${showInDetail ? 'show-in-detail' : ''} ${fieldType.toLowerCase()} ${editable ? 'editable' : ''}`}>
            <FieldName fieldType={fieldType} fieldName={field.fieldName} required={required} />
            <FieldValue field={field} onChange={onChange} />
        </div>
    )
}

const RecordFormLayout = ({ fields, componentType }) => {

    const toLowerCase = str => str.toLowerCase().replace(/\s+/g, '_');

    const [formData, setFormData] = useState(() =>
        Object.fromEntries(fields.map(({ fieldName, data }) => [toLowerCase(fieldName), data || ""]))
    );

    const handleChange = (fieldName, value) => {
        setFormData(prev => ({ ...prev, [toLowerCase(fieldName)]: value }));
    };

    return (
        <div id='record-form-layout' className='FCCS'>
            <div id='slider' className='FCCS'>
                {fields?.map((field) => (
                    <RecordField
                        key={field.fieldId}
                        field={{ ...field, data: formData[field.fieldId] }}
                        componentType={componentType}
                        onChange={handleChange}
                    />
                ))}
            </div>
        </div>
    );
};


export default RecordFormLayout